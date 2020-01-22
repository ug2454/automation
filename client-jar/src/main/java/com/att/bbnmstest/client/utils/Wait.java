package com.att.bbnmstest.client.utils;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;
import java.util.function.Predicate;


public class Wait
{
   private final long initialWait;
   private final long pollingInterval;

   public Wait(long initialWaitSeconds, long pollingIntervalSeconds)
   {
      this.initialWait = initialWaitSeconds;
      this.pollingInterval = pollingIntervalSeconds;
   }

   public <T, R> void until(long seconds, Supplier<R> supplier, Predicate<R> predicate) throws TimeoutException,
      InterruptedException
   {
      long wait = SECONDS.toMillis(initialWait);
      Thread.sleep(wait);

      Instant start = Instant.now();
      Instant max = start.plusSeconds(seconds);

      if (predicate.test(supplier.get()))
      {
         return;
      }

      while (Instant.now().isBefore(max))
      {
         long poll = SECONDS.toMillis(pollingInterval);
         Thread.sleep(poll);

         if (predicate.test(supplier.get()))
            return;
      }

      throw new TimeoutException();
   }

   public static void main(String[] args)
   {
      final Random rand = new Random();

      /**
       * wait 1 second, and then do the following:<br/>
       * 1. ask the supplier for the next integer<br/>
       * 2. if that value is less than 10, return true.<br/>
       * 3. Otherwise, wait 5 seconds and try again (go back to number 1).<br/>
       * If no value less than 10 is generated before 5 minutes expires, return false.
       */
      boolean result = Wait.untilCondition(() -> rand.nextInt(100), it -> it < 10, 1, 5, MINUTES.toSeconds(5));
   }

   public static <T> boolean untilCondition(Supplier<T> supplier, Predicate<T> predicate, int initialWaitInSeconds,
      int secondsBetweenPolls, long maxTimeoutSeconds)
   {
      try
      {
         new Wait(initialWaitInSeconds, secondsBetweenPolls).until(maxTimeoutSeconds, supplier, predicate);
      }
      catch (TimeoutException | InterruptedException te)
      {
         return false;
      }
      return true;
   }
}


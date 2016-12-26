package com.ilp.tcs.sitesafety.listeners;

/**
 * Created by 1241575 on 6/17/2016.
 * Listener interface for receiving json data from server.
 * {@link com.ilp.tcs.sitesafety.tasks.JsonTask} calling component must implement this interface
 */
public interface JsonListener {
    /**
     * @param s server result will come here as String
     */
    void onTaskComplete(String s);
}

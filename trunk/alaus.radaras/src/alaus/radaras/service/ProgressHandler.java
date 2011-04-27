/**
 * 
 */
package alaus.radaras.service;

/**
 * @author vienozin
 *
 */
public interface ProgressHandler {

    void progress(int currentProgress, int maxValue, String label);
    
}

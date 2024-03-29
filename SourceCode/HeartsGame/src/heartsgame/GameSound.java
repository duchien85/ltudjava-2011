/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package heartsgame;

import javax.sound.sampled.*;

import java.io.*;
/**
 *
 * @author BACDAIBAN
 */
public class GameSound {
    private AudioFormat format;
    private byte[] samples;

    /**
        Opens a sound from a file.
    */
    public GameSound(String filename) {
        try {
            // open the audio input stream
            AudioInputStream stream =
               AudioSystem.getAudioInputStream(getClass().getResource(filename));

            format = stream.getFormat();

            // get the audio samples
            samples = getSamples(stream);
        }
        catch (UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
        Gets the samples of this sound as a byte array.
    */
    public byte[] getSamples() {
        return samples;
    }


    /**
        Gets the samples from an AudioInputStream as an array
        of bytes.
    */
    private byte[] getSamples(AudioInputStream audioStream) {
        // get the number of bytes to read
        int length = (int)(audioStream.getFrameLength() *
            format.getFrameSize());

        // read the entire stream
        byte[] samples = new byte[length];
        DataInputStream is = new DataInputStream(audioStream);
        try {
            is.readFully(samples);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        // return the samples
        return samples;
    }


    /**
        Plays a stream. This method blocks (doesn't return) until
        the sound is finished playing.
    */
    public void play(InputStream source) {

        // use a short, 100ms (1/10th sec) buffer for real-time
        // change to the sound stream
        int bufferSize = format.getFrameSize() *
            Math.round(format.getSampleRate() / 10);
        byte[] buffer = new byte[bufferSize];

        // create a line to play to
        SourceDataLine line;
        try {
            DataLine.Info info =
                new DataLine.Info(SourceDataLine.class, format);
            line = (SourceDataLine)AudioSystem.getLine(info);
            line.open(format, bufferSize);
        }
        catch (LineUnavailableException ex) {
            ex.printStackTrace();
            return;
        }

        // start the line
        line.start();

        // copy data to the line
        try {
            int numBytesRead = 0;
            while (numBytesRead != -1) {
                numBytesRead =
                    source.read(buffer, 0, buffer.length);
                if (numBytesRead != -1) {
                   line.write(buffer, 0, numBytesRead);
                }
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        // wait until all data is played
        line.drain();

        // close the line
        line.close();

    }



}

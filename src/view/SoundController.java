package view;

import java.io.File;
import java.net.URI;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundController {
	public String song;
	public File file;
	public URI uri;
	public Media media;
	public MediaPlayer mediaPlayer;
	
	/**
	 * constructs the SoundController object
	 */
	public SoundController() {
		song = "./sounds/parlor_music_4.mp3";
		file = new File(song);
		uri = file.toURI();
		media = new Media(uri.toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setStopTime(media.getDuration());
		mediaPlayer.setAutoPlay(true);
		mediaPlayer.setOnEndOfMedia(new Runnable() {

			@Override
			public void run() {
				mediaPlayer.seek(new Duration(0));
			}});
	}
	
	/**
	 * plays a specified sound effect
	 * 
	 * @param sound: the file name of the sound to be played
	 */
	public void playSFX(String sound) {
		String sfx = "./sounds/" + sound;
		File fileSFX = new File(sfx);
		URI uriSFX = fileSFX.toURI();
		Media mediaSFX = new Media(uriSFX.toString());
		MediaPlayer mediaPlayerSFX = new MediaPlayer(mediaSFX);
		mediaPlayerSFX.play();
	}
}

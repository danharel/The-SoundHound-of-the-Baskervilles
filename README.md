# The SoundHound of the Baskervilles

### Usage

##### Initialization
1. Compile the program 
2. Run the program with Main.class

##### Loading the index
1. In the Indexer JFrame, select "File" in the menu.
2. Select Import File or Import Folder.
3. Select the file or folder you wish to import.

##### Identifying a clip
1. In the Identifier JFrame, select "File" in the menu.
2. Select "Load File".
3. Select the clip you wish to identify.
4. The most probable match will appear as the first result in the JFrame that appears.

### Unimplemented Features

* Histogram
* Playback of the matched clip
	There were two ways to implement this, both of which I decided weren't worth implementing
		+ Store the entire AudioClip in the database
			This would result in a massive database file.
			Storing just the probes for about 40 audio tracks results in a database size of over 80MB that takes 30 seconds to load.
			Storing the accompanying audio files would take far too long to load.
		+ Store the location of the audio file in the database and create a new instance of AudioClip when it's needed from that location.
			If the database is moved from one file system to another, then the audio file would need to move with it.

### Known Bugs

* The Waveform and Spectrogram are too long.
* Zooming in the first two times will not zoom in the Waveform.
* Every time you zoom in, the ranges of the JScrollPanes are cut in half. That is, if you zoom in twice, the JPanel only goes up until 1/4 of the way. 

### Unknown Bugs

None

# The SoundHound of the Baskervilles

### Usage

##### Initialization
1. Compile the program 
2. Run the program with Main.class
3. Locate the database file that you wish to use.

##### Loading the index
1. In the Indexer JFrame, select "File" in the menu.
2. Select Import File or Import Folder.
3. Select the file or folder you wish to import.

##### Identifying a clip
1. In the Identifier JFrame, select "File" in the menu.
2. Select "Load File".
3. Select the clip you wish to identify.
4. The most probable match will appear as the first result in the JFrame that appears.

### Known Bugs

* The Waveform and Spectrogram are too long.
* Zooming in the first two times will not zoom in the Waveform.
* Every time you zoom in, the ranges of the JScrollPanes are cut in half. That is, if you zoom in twice, the JPanel only goes up until 1/4 of the way. 

### Unknown Bugs

None

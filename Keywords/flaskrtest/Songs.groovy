package flaskrtest

public class Songs {

	private static final List<Map<String, String>> songs = [
		[
			'title': "ゆりかごの歌",
			'by': "北原白秋",
			'lyric': '''ゆりかごの歌を　かなりやがうたうよ　ねんねこ　ねんねこ　ねんねこよ''',
		],
	]

	static Map<String, String> song(int index) {
		return this.songs.get(index)
	}
}

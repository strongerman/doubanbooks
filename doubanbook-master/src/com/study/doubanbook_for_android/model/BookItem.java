package com.study.doubanbook_for_android.model;

import java.io.Serializable;
import java.util.List;

public class BookItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7594567088657327459L;
	private int id;// :"1003078",douban id
	private String isbn10;// :"7505715666",book isbn10
	private String isbn13;// :"9787505715660",
	private String title;// 标题OR图书名
	private String origin_title;// :"",MAYBE 原名
	private String alt_title;//
	private String subtitle;// :"",子标题
	private String url;// 图书详情URL :"http:\/\/api.douban.com\/v2\/book\/1003078",
	private String alt;// UNKNOWN:"http:\/\/book.douban.com\/subject\/1003078\/",
	private String image;// 图片默认最小:"http:\/\/img1.douban.com\/mpic\/s1001902.jpg",
	private ImageItem images; // 图书图片分大中小
	private List<String> author;// 作者":[
	// "（法）圣埃克苏佩里"
	// ],
	private List<String> translator;// 译者:[
	private String publisher;// :"中国友谊出版公司",
	private String pubdate;// :"2000-9-1",
	private Rating rating;// :{"max":10,"numRaters":9438,"average":"9.1","min":0},
	private List<TagItem> tags;// :[
	// {"count":2416,"name":"小王子"},
	// {"count":1914,"name":"童话"},
	// {"count":1185,"name":"圣埃克苏佩里"},
	// {"count":863,"name":"法国"},
	// {"count":647,"name":"经典"},
	// {"count":597,"name":"外国文学"},
	// {"count":495,"name":"感动"},
	// {"count":368,"name":"寓言"}
	// ],
	private String binding;// :"平装",
	private String price;// ":"19.8",
	private String pages;// ":"111v ",
	private String author_intro;// ":"圣埃克苏佩里（1900－1944）1900年，尼采逝世。这一年，安德烈・纪德在布鲁塞尔一次会议上宣称：“当今文学土地的面貌可以说是一片沼泽。”1900年，圣埃克苏佩里诞生。净化沼泽的意愿和能力历史地落在这个“世纪儿”的身上，圣埃克苏佩里是尼采式的第二代法国作家，拿但业的儿子，琐罗亚斯德的孙子，这个飞行员受到极大的遗传影响。灾种影响使他在探索、忧虑和英雄主义的道路上走到尽头。如尼采所说：“你应该超截止自己，走得更远，登得更高，直至群星在你脚下。”尼采成为他座舱中想象的伙伴。这个飞越沙漠和海洋的年轻驾驶员同样也遵循着纪德的教诲：“与其过宁静的生活，不如过悲怆的生活。”圣埃克苏佩里在他整个的一生中都在反复思考力量和热诚的真谛。\n圣埃克苏佩里（AntoinedeSaiot-Exupery），1900年出生于法国里昂，1921-1923年在法国空军中服役，曾是后备飞行员，后来又成为民用航空驾驶员，参加了开辟法国――非洲――南美国际航线的工作，其间他还从事文学写作，作品有《南线班机》（1930），《夜航》（1931）等等。\n1939年德国法西斯入侵法国，鉴于圣埃克苏佩里曾多次受伤，医生认为他不能再入伍参战；但经他坚决要求，参加了抗德战争，被编入2\/33空军侦察大队。1940年法国在战争中溃败，他所在的部队损失惨重，该部被调往阿尔及尔，随后即被复员，他只身流亡美国。在美国期间，他继续从事写作，1940年发表了《战斗飞行员》，1943年发表了《给一个人质的信》以及《小王子》。\n1943年，在他的强烈要求下，他回到法国在北非的抗战基地阿尔及尔。他的上级考虑到他的身体和年龄状况，只同意他执行五次飞行任务，他却要求到八次，1944年7月31日上午，他出航执行第八次任务，从此再也没有回来，牺牲时，年仅44岁。\n在欧洲某地的一个湖中，发现了圣・德克旭贝里的飞机残骸。这次搜索是经过对他最后一次出航的线路和德军当时的空军记录研究以后进行的，经过认证确认是那架失踪了半个世纪的侦察机。为了纪念这位伟大的战士和文学家，当地决定为这架飞机的残骸建立一个博物馆，以他的名字命名，陈列他的作品和遗物。",
	private String summary;// 图书简介":"小王子驾到！大家好，我是小王子，生活在B612星球，别看我是王子出生，我要做的事也不少，有时给花浇水，有时我还得耐心地把火山口通一通。实在闷得发慌的时候，为了找些事做，学点东西，我也访问一些其他的星球，像325号、326号、327号之类的。当然，我经历的事情也不少，有开心的，也有不开心的。这些事我通常会向地球上一个叫圣埃克苏佩里的人倾诉。对了，你可不要小瞧他，他是拿但业的儿子，琐罗亚斯德的孙子。他还被人们认为尼采式的第二代法国作家。他一生有两大爱好：飞行和写作。我之所以能够这样受欢迎也是他的功劳。因为他把我在其他星球的所见所闻编成了一本小书，也就是你们即将看到的这一本。它不但被誉为有史以来阅读仅次于《圣经》的书，全球发行的语言更是超过100种。可惜的是，在这本书出版后没多久，他在一次架机执行任务时一去不复返了，没有人知道他去了哪里。今天我第一次来到中国，还希望大家同样能够喜欢我。在这本书里他收藏了很多我在其他星球的精美彩图，而且，值得一提的是，中国著名的评论家周国平先生也特意为我作序。可以说，这本书不仅小朋友们爱不释手，就连大人们也会看得如痴如醉。糟糕，我还忘了告诉你，你只有在卓越网（www.joyo.com）才能找到我。有缘的话，我们很快就能相见了。\n尼采、纪德、圣埃克苏佩里是同一家庭的成员，由无可否认的联系连在一起。圣埃克苏佩里热爱尼采。纪德热爱圣埃克苏佩里。\n1945年2月1日《费加罗报》上，他谈到这位飞行员：\"他无论在何处着陆，都是为了带去欢乐。\"\n但是圣埃克苏佩里将公正置于友谊之上。他在《札记》中写道：\"纪德评价，却不曾体验。\"确切的见解。这是行动者面对思想者所感到的骄傲。尼采和纪德孕育了一种道德，并用美妙的文学冲动表现出来。只有圣埃克苏佩里一人在危险和充实中体验了这种道德。他是翱翔于九天的琐罗亚斯德，是乘风飞去的拿但业。他的书房便是机舱。他的格言：事事体验。他的作品：生活。圣埃克苏佩里对尼采的力量和纪德的热诚做作了合理的总结：他的冒险为职业，把写作当嗜好，他在飞行员的位置上实现着克尔桤郭尔的愿望：\"做一个思想家和做一个人，二者尽量不要区别开来，这样才是明智的。\"--（法）玛雅·戴斯特莱姆",
	private String catalog;// ":"序言：法兰西玫瑰\n小王子\n圣埃克苏佩里年表\n"
	private CurrenUserCollection current_user_collection;

	
	
	public CurrenUserCollection getCurrent_user_collection() {
		return current_user_collection;
	}

	public void setCurrent_user_collection(
			CurrenUserCollection current_user_collection) {
		this.current_user_collection = current_user_collection;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIsbn10() {
		return isbn10;
	}

	public void setIsbn10(String isbn10) {
		this.isbn10 = isbn10;
	}

	public String getIsbn13() {
		return isbn13;
	}

	public void setIsbn13(String isbn13) {
		this.isbn13 = isbn13;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOrigin_title() {
		return origin_title;
	}

	public void setOrigin_title(String origin_title) {
		this.origin_title = origin_title;
	}

	public String getAlt_title() {
		return alt_title;
	}

	public void setAlt_title(String alt_title) {
		this.alt_title = alt_title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public ImageItem getImages() {
		return images;
	}

	public void setImages(ImageItem images) {
		this.images = images;
	}

	public List<String> getAuthor() {
		return author;
	}

	public void setAuthor(List<String> author) {
		this.author = author;
	}

	public List<String> getTranslator() {
		return translator;
	}

	public void setTranslator(List<String> translator) {
		this.translator = translator;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPubdate() {
		return pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public List<TagItem> getTags() {
		return tags;
	}

	public void setTags(List<TagItem> tags) {
		this.tags = tags;
	}

	public String getBinding() {
		return binding;
	}

	public void setBinding(String binding) {
		this.binding = binding;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getAuthor_intro() {
		return author_intro;
	}

	public void setAuthor_intro(String author_intro) {
		this.author_intro = author_intro;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	@Override
	public String toString() {
		return "BookItem [id=" + id + ", isbn10=" + isbn10 + ", isbn13="
				+ isbn13 + ", title=" + title + ", origin_title="
				+ origin_title + ", alt_title=" + alt_title + ", subtitle="
				+ subtitle + ", url=" + url + ", alt=" + alt + ", image="
				+ image + ", images=" + images + ", author=" + author
				+ ", translator=" + translator + ", publisher=" + publisher
				+ ", pubdate=" + pubdate + ", rating=" + rating + ", tags="
				+ tags + ", binding=" + binding + ", price=" + price
				+ ", pages=" + pages + ", author_intro=" + author_intro
				+ ", summary=" + summary + ", catalog=" + catalog
				+ ", current_user_collection=" + current_user_collection + "]";
	}

}

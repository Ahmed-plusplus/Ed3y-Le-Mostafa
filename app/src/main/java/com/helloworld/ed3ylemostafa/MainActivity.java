package com.helloworld.ed3ylemostafa;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.helloworld.ed3o_le_mostafa.MyReceiver;
import com.helloworld.ed3o_le_mostafa.R;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> list;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.d3wat);
        list = new ArrayList<>();
        addList();
        lv.setAdapter(new ArrayAdapter<String>(this,R.layout.big_string,list));
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Vibrator vibrator = (Vibrator) MainActivity.this.getSystemService(VIBRATOR_SERVICE);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    vibrator.vibrate(VibrationEffect.createOneShot(50,-1));
                else
                    vibrator.vibrate(50);
                ClipboardManager clipboard = (ClipboardManager)MainActivity.this.getSystemService(CLIPBOARD_SERVICE);
                clipboard.setPrimaryClip(ClipData.newPlainText("text label",list.get(position)));
                Toast.makeText(MainActivity.this, "Copy is done", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        SharedPreferences sp = getSharedPreferences("ed3oloh",MODE_PRIVATE);
        if(sp.getBoolean("FIRST_TIME",true)) {
            boolean enter=false;
            for(final Intent intent: POWERMANAGER_INTENTS)
                if(getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)!=null) {
                    Log.e("MY_TAG", "match default only");
                    enter=true;
                    createAlarmAtSpecificTime(sp);
                }
            if(!enter)
                    createAlarmAtSpecificTime(sp);
        }
    }

    private void createAlarmAtSpecificTime(SharedPreferences sp) {
        Intent alarmIntent = new Intent(this, MyReceiver.class);
        PendingIntent pintent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BOOT_COMPLETED);
        registerReceiver(new MyReceiver(), filter);

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR, 10);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_HALF_DAY, pintent);
        //manager.
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("FIRST_TIME", false);
        editor.commit();
    }

    private void addList() {
        list.add("بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ ﴿١﴾ ٱلْحَمْدُ لِلَّهِ رَبِّ ٱلْعَٰلَمِينَ ﴿٢﴾ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ ﴿٣﴾ مَٰلِكِ يَوْمِ ٱلدِّينِ ﴿٤﴾ إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ ﴿٥﴾ ٱهْدِنَا ٱلصِّرَٰطَ ٱلْمُسْتَقِيمَ ﴿٦﴾ صِرَٰطَ ٱلَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ ٱلْمَغْضُوبِ عَلَيْهِمْ وَلَا ٱلضَّآلِّينَ ﴿٧﴾");
        list.add("أعوذ بالله من الشيطان الرجيم . اللَّـهُ لَا إِلَـٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ ۚ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ ۚ لَّهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الْأَرْضِ ۗ مَن ذَا الَّذِي يَشْفَعُ عِندَهُ إِلَّا بِإِذْنِهِ ۚ يَعْلَمُ مَا بَيْنَ أَيْدِيهِمْ وَمَا خَلْفَهُمْ ۖ وَلَا يُحِيطُونَ بِشَيْءٍ مِّنْ عِلْمِهِ إِلَّا بِمَا شَاءَ ۚ وَسِعَ كُرْسِيُّهُ السَّمَاوَاتِ وَالْأَرْضَ ۖ وَلَا يَئُودُهُ حِفْظُهُمَا ۚ وَهُوَ الْعَلِيُّ الْعَظِيمُ ﴿٢٥٥﴾");
        list.add("بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ . قُلْ هُوَ ٱللَّهُ أَحَدٌ ﴿١﴾ ٱللَّهُ ٱلصَّمَدُ ﴿٢﴾ لَمْ يَلِدْ وَلَمْ يُولَدْ ﴿٣﴾ وَلَمْ يَكُن لَّهُۥ كُفُوًا أَحَدٌۢ ﴿٤﴾");
        list.add("بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ . قُلْ أَعُوذُ بِرَبِّ ٱلْفَلَقِ ﴿١﴾ مِن شَرِّ مَا خَلَقَ ﴿٢﴾ وَمِن شَرِّ غَاسِقٍ إِذَا وَقَبَ ﴿٣﴾ وَمِن شَرِّ ٱلنَّفَّٰثَٰتِ فِى ٱلْعُقَدِ ﴿٤﴾ وَمِن شَرِّ حَاسِدٍ إِذَا حَسَدَ ﴿٥﴾");
        list.add("بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ . قُلْ أَعُوذُ بِرَبِّ ٱلنَّاسِ ﴿١﴾ مَلِكِ ٱلنَّاسِ ﴿٢﴾ إِلَٰهِ ٱلنَّاسِ ﴿٣﴾ مِن شَرِّ ٱلْوَسْوَاسِ ٱلْخَنَّاسِ ﴿٤﴾ ٱلَّذِى يُوَسْوِسُ فِى صُدُورِ ٱلنَّاسِ ﴿٥﴾ مِنَ ٱلْجِنَّةِ وَٱلنَّاسِ ﴿٦﴾");
        list.add("اللَّهُمَّ أبدله داراً خيراً من داره، وأهلاً خيراً من أهله، وأدخله الجنّة، وأعذه من عذاب القبر ومن عذاب النّار");
        list.add("اللَّهُمَّ عامله بما أنت أهله، ولا تعامله بما هو أهله");
        list.add("اللَّهُمَّ اجزه عن الإحسان إحساناً وعن الإساءة عفواً وغفراناً");
        list.add("اللهم عبدُك وابن أمتك، احتاج إلى رحمتك، وأنت غنىٌ عن عذابه، اللَّهُمَّ إن كان محسناً فزد من حسناته، وإن كان مسيئاً فتجاوز عن سيّئاته");
        list.add("اللَّهُمَّ أدخله الجنّة من غير مناقشة حساب ولا سابقة عذاب");
        list.add("اللَّهُمَّ آنسه في وحدته وفي وحشته وفي غربته");
        list.add("اللَّهُمَّ أنزله منزلاً مباركاً وأنت خير المنزلين");
        list.add("اللَّهُمَّ أنزله منازل الصدّيقين والشّهداء والصّالحين، وحسن أولئك رفيقاً");
        list.add("اللَّهُمَّ اجعل قبره روضةً من رياض الجنّة، ولا تجعله حفرةً من حفر النّار");
        list.add("اللَّهُمَّ افسح له في قبره مدّ بصره، وافرش قبره من فراش الجنّة");
        list.add("اللَّهُمَّ أعذه من عذاب القبر، وجفاف ِالأرض عن جنبيها");
        list.add("اللَّهُمَّ املأ قبره بالرّضا والنّور والفسحة والسّرور");
        list.add("اللَّهُمَّ إنّ مصطفى بن عبد العزيز في ذمّتك وحبل جوارك، فقِهِِ فتنة القبر، وعذاب النّار، وأنت أهل الوفاء والحقّ، فاغفر له وارحمه إنّك أنت الغفور الرّحيم");
        list.add("اللَّهُمَّ إنّه عبدك وابن عبدك خرج من الدّنيا وسعتها ومحبوبها وأحبّائه فيها إلى ظلمة القبر وما هو لاقيه");
        list.add("اللَّهُمَّ إنّه كان يشهد أنّك لا إله إلّا أنت وأنّ محمّداً عبدك ورسولك وأنت أعلم به");
        list.add("اللَّهُمَّ إنّا نتوسّل بك إليك، ونقسم بك عليك أن ترحمه ولا تعذّبه، وأن تثبّته عند السؤال");
        list.add("اللَّهُمَّ إنّه نَزَل بك وأنت خير منزولٍ به، وأصبح فقيراً إلى رحمتك وأنت غنيٌّ عن عذابه");
        list.add("اللَّهُمَّ آته برحمتك ورضاك، وقهِ فتنة القبر وعذابه، وآته برحمتك الأمن من عذابك حتّى تبعثه إلى جنّتك يا أرحم الرّاحمين");
        list.add("اللَّهُمَّ انقله من مواطن الدّود وضيق اللحود إلى جنّات الخلود");
        list.add("اللَّهُمَّ احمه تحت الأرض، واستره يوم العرض، ولا تخزه يوم يبعثون \"يوم لا ينفع مالٌ ولا بنون إلّا من أتى الله بقلبٍ سليم\"");
        list.add("اللَّهُمَّ يمّن كتابه، ويسّر حسابه، وثقّل بالحسنات ميزانه، وثبّت على الصّراط أقدامه، وأسكنه في أعلى الجنّات بجوار حبيبك ومصطفاك (صلّى الله عليه وسلّم)");
        list.add("اللَّهُمَّ أمّنه من فزع يوم القيامة، ومن هول يوم القيامة، واجعل نفسه آمنة مطمئنّة، ولقّنه حجّته");
        list.add("اللَّهُمَّ اجعله في بطن القبر مطمئنّاً وعند قيام الأشهاد آمن، وبجود رضوانك واثق، وإلى أعلى درجاتك سابق");
        list.add("اللَّهُمَّ اجعل عن يمينه نوراً حتّى تبعثه آمناً مطمئنّاً في نورٍ من نورك");
        list.add("اللَّهُمَّ انظر إليه نظرة رضا، فإنّ من تنظر إليه نظرة رضا لا تعذّبه أبداً");
        list.add("اللَّهُمَّ أسكنه فسيح الجنان، واغفر له يا رحمن، وارحمه يا رحيم، وتجاوز عمّا تعلم يا عليم");
        list.add("اللَّهُمَّ اعف عنه فإنّك القائل \"ويعفو عن كثير\"");
        list.add("اللَّهُمَّ إنّه جاء ببابك، وأناخ بجنابك، فَجد عليه بعفوك وإكرامك وجود إحسانك");
        list.add("اللَّهُمَّ إنّ رحمتك وسعت كلّ شيء فارحمه رحمةً تطمئنّ بها نفسه، وتقرّ بها عينه");
        list.add("اللَّهُمَّ يا رحمن احشره مع المتّقين إليك وفداً");
        list.add("اللَّهُمَّ احشره مع أصحاب اليمين، واجعل تحيّته سلامٌ لك من أصحاب اليمين");
        list.add("اللَّهُمَّ بشّره بقولك \"كلوا واشربوا هنيئاً بما أسلفتم في الأيّام الخالية\"");
        list.add("اللَّهُمَّ اجعله من الّذين سعدوا في الجنّة خالدين فيها ما دامت السموات والأرض");
        list.add("اللَّهُمَّ لا نزكّيه عليك، ولكنّا نحسبه أنّه أمن وعمل صالحاً، فاجعل له جنّتين ذواتي أفنان بحقّ قولك: \"ولمن خاف مقام ربّه جنّتان\"");
        list.add("اللَّهُمَّ شفع فيه نبيّنا ومصطفاك، واحشره تحت لوائه، واسقه من يده الشّريفة شربةً هنيئةً لا يظمأ بعدها أبداً");
        list.add("اللَّهُمَّ اجعله في جنّة الخلد \"الَّتِي وُعِدَ الْمُتَّقُونَ كَانَتْ لَهُمْ جَزَاء وَمَصِيرًا. لَهُمْ فِيهَا مَا يَشَاؤُونَ خَالِدِينَ كَانَ عَلَى رَبِّكَ وَعْدًا مَسْؤُولا\"");
        list.add("اللَّهُمَّ إنّه صبر على البلاء فلم يجزع، فامنحه درجة الصّابرين الّذين يوفون أجورهم بغير حساب فإنّك القائل \"إنّما يوفي الصّابرون أجرهم بغير حساب\"");
        list.add("اللَّهُمَّ إنّه كان مصلّ لك، فثبّته على الصّراط يوم تزل الأقدام");
        list.add("اللَّهُمَّ إنّه كان صائماً لك، فأدخله الجنّة من باب الريّان");
        list.add("اللَّهُمَّ إنّه كان لكتابك تالٍ وسامع، فشفّع فيه القرآن، وارحمه من النّيران، واجعله يا رحمن يرتقي في الجنّة إلى آخر آية قرأها أو سمعها، وآخر حرفٍ تلاه");
        list.add("اللَّهُمَّ ارزقه بكلّ حرفٍ في القرآن حلاوة، وبكلّ كلمة كرامة، وبكلّ اّية سعادة، وبكلّ سورة سلامة، وبكل جْزءٍ جزاء");
        list.add("اللَّهُمَّ ارحمه فإنّه كان مسلماً، واغفر له فإنّه كان مؤمناً، وأدخله الجنّة فإنّه كان بنبيّك مصدّقاً، وسامحه فإنّه كان لكتابك مرتّلاً");
        list.add("اللَّهُمَّ اغفر لحيّنا وميّتنا، وشاهدنا وغائبنا، وصغيرنا وكبيرنا، وذَكرنَا وأنثانا");
        list.add("اللَّهُمَّ من أحييته منّا فأحيه على الإسلام، ومن توفّيته منّا فتوفّه على الإيمان");
        list.add("اللَّهُمَّ لا تحرمنا أجره ولا تضللنا بعده");
        list.add("اللَّهُمَّ ارحمنا إذا أتانا اليقين، وعرق منّا الجبين، وكثر الأنين والحنين");
        list.add("اللَّهُمَّ ارحمنا إذا يئس منّا الطبيب، وبكى علينا الحبيب، وتخلّى عنّا القريب والغريب، وارتفع النّشيج والنّحيب");
        list.add("اللَّهُمَّ ارحمنا إذا اشتدّت الكربات، وتوالت الحسرات، وأطبقت الرّوعات، وفاضت العبرات، وتكشّفت العورات، وتعطّلت القوى والقدرات");
        list.add("اللَّهُمَّ ارحمنا إذا حُمِلنا على الأعناقِ، وبلغتِ التراقِ، وقيل من راق وظنّ أنّه الفراق والتفَّتِ السَّاقُ بالسَّاقِ، إليك يا ربَّنا يومئذٍ المساق");
        list.add("اللَّهُمَّ ارحمنا إذا ورينا التّراب، وغلقت القبور والأبواب، وانفضّ الأهل والأحباب، فإذا الوحشة والوحدة وهول الحساب");
        list.add("اللَّهُمَّ ارحمنا إذا فارقنا النّعيم، وانقطع النّسيم، وقيل ما غرّك بربّك الكريم");
        list.add("اللَّهُمَّ ارحمنا إذا أقمنا للسؤال، وخاننا المقال، ولم ينفع جاهٌ ولامال ولا عيال، وقد حال الحال، وليس إلّا فضل الكبير المتعال");
        list.add("اللَّهُمَّ ارحمنا إذا نَسي اسمنا، ودَرس رسمنا، وأحاط بنا قسمنا ووسعنا");
        list.add("اللَّهُمَّ ارحمنا إذا أهملنا فلم يزرنا زائر، ولم يذكرنا ذاكر، وما لنا من قوّة ولا ناصر، فلا أمل إلّا في القاهر القادر الغافر، يا من إذا وعد أوفى، وإذا توعّد عفا، وشفّع يا ربّ فينا حبيبنا المصطفى، واجعلنا ممّن صفا ووفا، وبالله اكتفى، يا أرحم الرّاحمين، يا حيّ يا قيّوم، يا بديع السموات والأرض، يا ذا الجلال والإكرام");
        list.add("اللَّهُمَّ إنّه عبدك وابن عبدك وابن أمتك مات وهو يشهد لك بالوحدانيّة ولرسولك بالشّهادة فاغفر له إنّك أنت الغفّار");
        list.add("اللَّهُمَّ لا تحرمنا أجره، ولا تفتنّا بعده، واغفر لنا وله، واجمعنا معه في جنّات النّعيم يا ربّ العالمين");
        list.add("اللهم اغفر له وارحمه، وعافه واعف عنه، وأكرم نزله، ووسع مدخله، واغسله بالماء والثلج والبرد، ونقه من الخطايا كما نقيت الثوب الأبيض من الدنس");
        list.add("اللهم اغفر له، وارفع درجته فى المهديين، واخلفه فى عقِبِهِ فى الغابرين، واغفر لنا وله يا رب العالمين، وأفسح له فى قبرِهِ ونور له فيه");
        list.add("اللَّهُمَّ أنزل على أهله الصّبر والسلوان وارضهم بقضائك");
        list.add("اللَّهُمَّ ثبّتنا على القول الثّابت في الحياة الدّنيا، وفي الآخرة، ويوم يقوم الأشهاد");
        list.add("اللَّهُمَّ صلّ وسلّم وبارك على سيّدنا محمّد، وعلى اّله وصحبه وسلّم إلى يوم الدّين");
    }

    private static final Intent[] POWERMANAGER_INTENTS = {
            new Intent().setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity")),
            new Intent().setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity")),
            new Intent().setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity")),
            new Intent().setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity")),
            new Intent().setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity")),
            new Intent().setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.startupapp.StartupAppListActivity")),
            new Intent().setComponent(new ComponentName("com.oppo.safe", "com.oppo.safe.permission.startup.StartupAppListActivity")),
            new Intent().setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity")),
            new Intent().setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager")),
            new Intent().setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity")),
            new Intent().setComponent(new ComponentName("com.samsung.android.lool", "com.samsung.android.sm.ui.battery.BatteryActivity")),
            new Intent().setComponent(new ComponentName("com.htc.pitroad", "com.htc.pitroad.landingpage.activity.LandingPageActivity")),
            new Intent().setComponent(new ComponentName("com.asus.mobilemanager", "com.asus.mobilemanager.MainActivity"))};

}

package com.newer.mymusicplayer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import com.newer.service.MusicService;
import com.newer.tool.Tools;

public class MusicPlay extends Activity implements OnSeekBarChangeListener{

	private static final int MENU_EXIT = 0;
	private TextView mtvTitle;// ������
	private TextView mtvSinger;// ������
	private ImageView mivImage;// ר��ͼƬ
	private TextView mtvCurrentTime;// ��ǰʱ��
	private TextView mtvTotalTime;// ��ʱ��
	private Intent intent;
	private SeekBar mSeekBar;//������
	private ImageView mivMode;//����ģʽ

	private ImageButton imPlay;

	private int musicId;
	private int[] ids;
	private String[] titles;
	private int position;
	private int[] albumIds;
	private String[] singers;
	private int[] totalTimes;
	private NotificationManager nm;

	private boolean isPlay = true;// �жϲ���/��ͣ
	
	private int currenttime;
	private int totaltime;
	private int loopFlag;
	private boolean isBind;//�Ƿ�󶨷���
	
	private MusicService musicService;
	//ע��㲥������
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Tools.ACTION_CTIME)) {//��ȡ��ǰʱ��
				currenttime = intent.getIntExtra("currentTime", -1);
				mtvCurrentTime.setText(Tools.format(currenttime));
				mSeekBar.setProgress(currenttime);
				
			} else if (action.equals(Tools.ACTION_TTIME)) {//��ȡ��ʱ��
				totaltime = intent.getIntExtra("totaltime", -1);
				mtvTotalTime.setText(Tools.format(totaltime));
				mSeekBar.setMax(totaltime);
				
			} else if (action.equals(Tools.ACTION_COMPLETION)) {//��һ�׸貥�����Ժ�
				int loopmode = intent.getIntExtra("loop", -1);
//				Toast.makeText(MusicPlay.this, loopmode + "", 2000).show();
				switch (loopmode) {
				case Tools.LOOP_SINGER://����ѭ��
					play();
					break;

				case Tools.LOOP_ORDER://˳�򲥷�
					next();
					break;
					
				case Tools.LOOP_RANDOM://�������
					randomPlay();
					break;
					
				default:
					next();
				}
				
			}
		}

	};
	
	//�������
	private void randomPlay() {
		int rand = (int)(Math.random() * ids.length + 1);
		if (rand - 1 == position) {
			randomPlay();
		} else {
			position = rand -1;
			initData();
			play();
		}
	}
	
	//�󶨷���
	private ServiceConnection conn = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			musicService = null;
			isBind = false;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			musicService = ((MusicService.LocalBinder) service).getService();
			isBind = true;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.musicplay);
		initView();
		getIntentData();
		initData();
		play();
	}
	
	@Override
	protected void onStop() {
		if (isBind) {
			unbindService(conn);
			isBind = false;
		}
		super.onStop();
	}

	//����֪ͨ
	private void sendNotification() {
		nm = (NotificationManager) 
				getSystemService(NOTIFICATION_SERVICE);
		Notification notification = new Notification
				(android.R.drawable.ic_media_play, "���ֲ�����", 
						System.currentTimeMillis());
		Intent intent = new Intent(this, MusicPlay.class);
		intent.putExtra("position", position);
		intent.putExtra("titles", titles);
		intent.putExtra("singers", singers);
		intent.putExtra("albumIds", albumIds);
		intent.putExtra("ids", ids);
		intent.putExtra("totalTimes", totalTimes);
		PendingIntent contentIntent = PendingIntent.getActivity
				(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(
				this, titles[position], singers[position], contentIntent );
		notification.flags = Notification.FLAG_ONGOING_EVENT;
		nm.notify(1, notification);
	}

	//��ȡintent������������
	private void getIntentData() {
		intent = getIntent();
		Bundle bundle = intent.getExtras();
		titles = bundle.getStringArray("titles");// ����
		position = bundle.getInt("position");// λ��
		albumIds = bundle.getIntArray("albumIds");// ר��id
		ids = bundle.getIntArray("ids");// ����id
		singers = bundle.getStringArray("singers");// ������
		totalTimes = bundle.getIntArray("totalTimes");// ��ʱ��
	}

	// ��ʼ������
	private void initData() {

		musicId = ids[position];

		mtvTitle.setText(titles[position]);
		mtvSinger.setText(singers[position]);
		mivImage.setImageBitmap(Tools.getArtPic(this, String.valueOf(musicId)));
		mtvTotalTime.setText(Tools.format(totalTimes[position]));
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(Tools.ACTION_CTIME);
		filter.addAction(Tools.ACTION_TTIME);
		filter.addAction(Tools.ACTION_COMPLETION);
		registerReceiver(receiver , filter);
		mSeekBar.setOnSeekBarChangeListener(this);
	}

	// ��ʼ���ؼ�
	private void initView() {
		mtvTitle = (TextView) findViewById(R.id.textView_title);
		mtvSinger = (TextView) findViewById(R.id.textView_singer);
		mivImage = (ImageView) findViewById(R.id.imageView);
		mtvCurrentTime = (TextView) findViewById(R.id.textView_currenttime);
		mtvTotalTime = (TextView) findViewById(R.id.textView_totaltime);
		imPlay = (ImageButton) findViewById(R.id.ib_play);
		mSeekBar = (SeekBar) findViewById(R.id.seekBar);
		mivMode = (ImageButton) findViewById(R.id.imageView_mode);
	}
	
	//ѭ������ģʽ
	public void loopMode(View view) {
		loopFlag ++;
		switch (loopFlag) {
		case 1://����ѭ��
			mivMode.setImageResource(R.drawable.mode_single_loop);
			Toast.makeText(this, "����ѭ��", Toast.LENGTH_SHORT).show();
			musicService.setLoopMode(Tools.LOOP_SINGER);
			break;
			
		case 2://�������
			mivMode.setImageResource(R.drawable.mode_random);
			Toast.makeText(this, "�������", Toast.LENGTH_SHORT).show();
			musicService.setLoopMode(Tools.LOOP_RANDOM);
			break;
			
		case 3://˳�򲥷�
			mivMode.setImageResource(R.drawable.mode_list_loop);
			Toast.makeText(this, "˳�򲥷�", Toast.LENGTH_SHORT).show();
			musicService.setLoopMode(Tools.LOOP_ORDER);
			loopFlag = 0;
			break;
			
		default:
			break;
		}
	}

	//���ŵ��¼������ţ���ͣ��ֹͣ����һ�ף���һ��
	public void PlayMode(View view) {

		switch (view.getId()) {
		case R.id.ib_stop:// ֹͣ
			stop();
			nm.cancel(1);
			break;

		case R.id.ib_pre:// ��һ��
			prev();
			sendNotification();
			break;

		case R.id.ib_play:// ����
			if (isPlay) {
				play();
			} else {
				pause();
				nm.cancel(1);
			}
			break;

		case R.id.ib_next:// ��һ��
			next();
			sendNotification();
			break;

		case R.id.ib_menu://�˳�
			showDialog(MENU_EXIT);
			break;
			
		default:
			break;
		}
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case MENU_EXIT:
			
			return DialogExit();

		default:
			return null;
		}
	}

	private Dialog DialogExit() {
		// TODO Auto-generated method stub
		return new AlertDialog.Builder(this).setMessage("�Ƿ��˳�?")
				.setPositiveButton("��", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						exit();
					}
				})
				.setNegativeButton("��", null)
				.create();
	}
	
	//�˳�
	private void exit() {
		unbindService(conn);
		isBind = false;
		nm.cancel(1);//ȡ��֪ͨ
		musicService.removeHandler();
		stopService(intent);
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	//��ͣ
	private void pause() {
		isPlay = true;
		imPlay.setImageResource(R.drawable.player_play);
		intent = new Intent();
		intent.setAction("com.newer.music_SEVICE");
		intent.putExtra("musicId", musicId);
		intent.putExtra("mode", Tools.PAUSE);
		startService(intent);

	}

	//��һ��
	private void next() {
		if (position == ids.length - 1) {
			position = 0;
		} else {
			position ++;
		}
		initData();
		play();
	}

	//����
	private void play() {
		isPlay = false;
		imPlay.setImageResource(R.drawable.player_pause);
		intent = new Intent();
		intent.setAction("com.newer.music_SEVICE");
		intent.putExtra("musicId", musicId);
		intent.putExtra("mode", Tools.PLAY);
		startService(intent);
		bindService(intent, conn , BIND_AUTO_CREATE);
		sendNotification();
	}
	
	

	//��һ��
	private void prev() {
		if (position == 0) {
			position = ids.length - 1;
		} else {
			position--;
		}
		initData();
		play();
	}

	//ֹͣ
	private void stop() {
		imPlay.setImageResource(R.drawable.player_play);
		isPlay = true;
		intent = new Intent();
		intent.setAction("com.newer.music_SEVICE");
		intent.putExtra("musicId", musicId);
		intent.putExtra("mode", Tools.STOP);
		startService(intent);
	}

	//�϶��������¼�
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (fromUser) {
			Intent intent = new Intent();
			intent.setAction("com.newer.music_SEVICE");
			intent.putExtra("mode", Tools.PROGRESS);
			intent.putExtra("progress", progress);
			startService(intent);
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		pause();
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		play();
	}
}

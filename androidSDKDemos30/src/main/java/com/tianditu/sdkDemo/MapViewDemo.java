package com.tianditu.sdkDemo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.overlay.PolygonOverlay;
import com.tianditu.android.maps.renderoption.PlaneOption;

public class MapViewDemo extends Activity {
	private MapView mMapView;
	private RadioGroup mRadioGroup;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.mapviewdemo);

		// 地图视图
		mMapView = (MapView) findViewById(R.id.mapview);
		mMapView.setLogoPos(MapView.LOGO_LEFT_TOP);
		mMapView.setBuiltInZoomControls(true);
		mMapView.enableRotate(true);
//		mMapView.setCustomTileService("http://t1.tianditu.cn/DataServer?");
//		Log.w("tianditu", "custom:" + mMapView.isCustomTileService());

		// 地图类型
		mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		int mapType = mMapView.getMapType();
		if (mapType == MapView.TMapType.MAP_TYPE_VEC)
			mRadioGroup.check(R.id.radio0);
		else if (mapType == MapView.TMapType.MAP_TYPE_IMG)
			mRadioGroup.check(R.id.radio1);
		else if (mapType == MapView.TMapType.MAP_TYPE_TERRAIN)
			mRadioGroup.check(R.id.radio2);

		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.radio0)
					mMapView.setMapType(MapView.TMapType.MAP_TYPE_VEC);
				else if (checkedId == R.id.radio1)
					mMapView.setMapType(MapView.TMapType.MAP_TYPE_IMG);
				else if (checkedId == R.id.radio2)
					mMapView.setMapType(MapView.TMapType.MAP_TYPE_TERRAIN);
			}
		});

		
		CheckBox cb2 = (CheckBox) this.findViewById(R.id.isWebMercator);
		cb2.setChecked(mMapView.isWebMercatorCRS());
		cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				mMapView.toggleCoordinateSys();
			}
		});

//		CheckBox cb = (CheckBox) this.findViewById(R.id.useVectorTile);
//		cb.setChecked(mMapView.getVectorDrawTile());
//		cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView,
//					boolean isChecked) {
//				mMapView.setVectorDrawTile(isChecked);
//			}
//		});

//		 addPolygon();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mMapView.onDestroy();
		super.onDestroy();
	}

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            finish();
        return super.onKeyUp(keyCode, event);
    }
    
	private void addPolygon() {
		ArrayList<GeoPoint> points = new ArrayList<GeoPoint>();

		String polyStr = "110.972760146264 26.3867227289708,111.286759259732 26.2655407398133,111.188863999974 25.9605529483599,111.244345813808 25.8701840041885,111.489495741635 25.8725954364303,111.302642234052 25.7225477348901,111.295580308584 25.4513506581064,110.994383504895 25.1664807260047,110.964582612697 24.9709202170944,111.092274680832 24.9420821300863,111.103358770191 25.0416979278172,111.262206498032 25.1494353896315,111.398773758702 25.1305262047206,111.468408620861 25.0210981703443,111.426032682962 24.686492543696,111.55566384501 24.6422475915531,111.701990604283 24.7928944700038,111.996855097984 24.761839615306,111.922561666688 24.6329958753594,112.053747796124 24.3407541419901,111.87124037924 24.228971008905,111.93621235012 23.985055852046,111.817655001777 23.9140102431782,111.806407435455 23.8101794300367,111.649533691627 23.8364588756766,111.659844804694 23.7030003473882,111.481709155953 23.629106807733,111.394016652646 23.4720386637534,111.344092066296 23.2746858743537,111.429138061772 23.0407522164417,111.354173706551 22.8926666728287,111.214105417531 22.7507296573478,111.052476688249 22.7326312193553,111.050632506985 22.6510091677389,110.756907152143 22.5877917714731,110.73853243972 22.4685899779452,110.67719945679 22.4770238506702,110.782302239557 22.2855529035437,110.64917161218 22.2430799341917,110.626803731807 22.1517350115616,110.344236533589 22.1986846297919,110.387263086922 21.8960443107964,109.982138447631 21.8820275313456,109.898937716203 21.655686870153,109.752086819451 21.6737493604447,109.751118125501 21.4583413670605,109.563617471169 21.5000094359867,109.501116572872 21.4166760752407,109.063614113201 21.3750119435464,109.001114669107 21.5000129505373,109.126115303778 21.5000121699258,109.188616211969 21.5833455243765,108.876114640578 21.583347477704,108.856326720002 21.7626267933919,108.857627371706 21.5853361629336,108.73600234773 21.5974635126311,108.70335509766 21.7019406199637,108.604199810373 21.7004596218142,108.57150353466 21.8166422860127,108.540397317882 21.761939628611,108.475864208966 21.8406466492235,108.549766635722 21.7530128329759,108.478575442802 21.6989340973308,108.570974794451 21.6864980931126,108.519563647644 21.596358733269,108.393184199391 21.5536955222306,108.480560466892 21.6660839754951,108.333052910364 21.6900479969176,108.303518875796 21.5427894446772,108.21724758191 21.4981262457297,108.274621742 21.5998246412942,108.203054459796 21.6418797622413,108.128852253029 21.5176552811664,107.954952972302 21.5366207240672,107.857399586222 21.654632549053,107.542758609522 21.5891540611111,107.461946529609 21.6637413359939,107.382494299273 21.5958497934333,107.301523635607 21.7422748369142,107.221514477611 21.7079279392231,107.008474650984 21.8278020488256,107.024735208363 21.9452993069175,106.766385035141 22.0125383778439,106.690821154972 21.9642263298813,106.690350975917 22.2779457038312,106.559207950087 22.3488203066119,106.557008190376 22.4587168850762,106.611002713504 22.6052010248075,106.724264410274 22.5879579517981,106.82814427334 22.8135695712787,106.52173433717 22.9486255482305,106.278335675351 22.8693161176448,106.145334168137 22.9960934070851,105.876195451559 22.9171779595952,105.709804827331 23.0722822863984,105.559661379469 23.0880812091805,105.530585141191 23.2450916373692,105.697732532331 23.331553037143,105.625990247452 23.4045901847892,105.864595797958 23.5354517938653,106.005702303019 23.452200675221,106.137238556514 23.5757391732243,106.196668380641 23.8766741581859,106.045723660219 24.0907036509528,105.918305239595 24.1240436110239,105.887894256315 24.042254333171,105.647625567066 24.0325540396373,105.59325226478 24.1411112964492,105.489918125621 24.0198498315046,105.317120494615 24.1200649230578,105.255165390371 24.0635676954928,105.178676074541 24.135388809546,105.241576157614 24.2076400837229,105.155712804303 24.2833291962854,105.196461853332 24.3312863789633,105.105936469393 24.4161075052572,104.753368382245 24.4606261867492,104.696983424246 24.3200737432088,104.447074199615 24.6431432722443,104.52987630205 24.7337736178582,104.741421097506 24.624907610298,105.030913678923 24.7909424465367,105.033907555289 24.8731406767009,105.20680285267 24.9986892646979,105.43932465321 24.9249403737992,105.496017003082 24.8106181191309,105.934813421702 24.7298444546579,106.018934816089 24.6355742766316,106.194387062313 24.8181490617299,106.144917684004 24.9613844383412,106.993270717869 25.2432697957645,106.960186101407 25.4407643318085,107.202049594413 25.6143855808299,107.325273890877 25.4988530104581,107.305282410555 25.4113986242472,107.418425558693 25.3890118500169,107.46868036081 25.2161259847265,107.663237159767 25.3223611074792,107.763714042169 25.1194128900929,108.110521527347 25.2123233804197,108.154382550814 25.4438251132385,108.242786684058 25.4289677383705,108.329803866655 25.540016494066,108.621197739884 25.3088816252868,108.584025277545 25.4247129360532,108.686448393579 25.520204451874,108.680271321864 25.6249710932414,108.782528443733 25.6244344866623,108.807161022963 25.528895680891,109.072930653427 25.5381108783145,109.049985425387 25.7479739946042,108.891149795227 25.7159404724749,109.113764532807 25.8125331973492,109.143025079044 25.7442514542413,109.172274660747 25.8093909463352,109.336474353908 25.7356201938169,109.478780270393 26.0338574113905,109.647874071431 26.0513218965883,109.725907685991 25.9945719163609,109.679706253341 25.8861089111974,109.776067258897 25.8697010682498,109.785737953943 26.0175148718139,109.964397185282 26.2045339230022,110.09352385734 26.1722782629315,110.058755831417 26.041193493385,110.187251633556 26.0714118194,110.302864825451 25.9706209575912,110.60904710544 26.3365174543171,110.922391056502 26.2562969844183,110.972760146264 26.3867227289708";
		String[] tmps = polyStr.split(",");
		for (String string : tmps) {

			String[] t = string.split(" ");
			points.add(new GeoPoint((int) (Double.parseDouble(t[1]) * 1E6),
					(int) (Double.parseDouble(t[0]) * 1E6)));
		}

		PlaneOption option = new PlaneOption();
		option.setFillColor(0xAAFF0000);
		option.setStrokeColor(0xFF000000);
		option.setStrokeWidth(5);
		option.setDottedLine(true);

		PolygonOverlay overlay = new PolygonOverlay();
		overlay.setOption(option);
		overlay.setPoints(points);
		mMapView.addOverlay(overlay);

	}
}

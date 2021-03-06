package spork.android.test.bindresource.domain;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import javax.annotation.Nullable;

import spork.Spork;
import spork.android.BindFragment;
import spork.android.BindLayout;
import spork.android.BindResource;
import spork.android.BindView;
import spork.android.test.R;
import spork.android.test.bindresource.ResourceProvider;

@BindLayout(R.layout.activity_resource_binding)
public class TestActivity extends android.app.Activity implements ResourceProvider {

    @BindResource(R.string.app_name)
    private String appName;

    @BindResource
    private String app_name;

    @BindResource(R.dimen.spork_test_dimension)
    private float sporkTestDimension;

    @BindResource
    private Float spork_test_dimension;

    @BindResource(R.drawable.spork_test_drawable)
    private Drawable sporkTestDrawable;

    @BindResource
    private Drawable spork_test_drawable;

    @BindResource
    private int spork_test_int;

    @BindResource(R.integer.spork_test_int)
    private Integer spork_test_integer;

    @BindResource
    private boolean spork_test_boolean;

    @BindResource(R.bool.spork_test_boolean)
    private Boolean spork_test_boolean_object;

    @BindFragment(R.id.resourcebindingfragment)
    private TestFragment resourceBindingFragment;

    @BindView(R.id.resourcebindingview)
    private TestView resourceBindingView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Spork.bind(this);
    }

    @Nullable
    @Override
    public String getStringByIdSpecified() {
        return appName;
    }

    @Nullable
    @Override
    public String getStringByIdImplied() {
        return app_name;
    }

    @Nullable
    @Override
    public Drawable getDrawableByIdSpecified() {
        return sporkTestDrawable;
    }

    @Nullable
    @Override
    public Drawable getDrawableByIdImplied() {
        return spork_test_drawable;
    }

    @Override
    public int getIntByIdImplied() {
        return spork_test_int;
    }

    @Override
    public Integer getIntegerByIdSpecified() {
        return spork_test_integer;
    }

    @Override
    public float getDimensionByIdSpecified() {
        return sporkTestDimension;
    }

    @Override
    public float getDimensionByIdImplied() {
        return spork_test_dimension;
    }

    @Override
    public boolean getBooleanByIdImplied() {
        return spork_test_boolean;
    }

    @Nullable
    @Override
    public Boolean getBooleanByIdSpecified() {
        return spork_test_boolean_object;
    }

    public TestFragment getResourceBindingFragment() {
        return resourceBindingFragment;
    }

    public TestView getResourceBindingView() {
        return resourceBindingView;
    }
}

package com.chestnut.Dialog.XAlertDialog;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * <pre>
 *     author: Chestnut
 *     blog  : http://www.jianshu.com/u/a0206b5f4526
 *     time  : 2017/6/23 17:06
 *     desc  :
 *     thanks To:
 *     dependent on:
 *     update log:
 * </pre>
 */
public class Roll3DAnimation extends Animation{

    // 旋转点类型 默认为 ABSOLUTE
    private int mPivotXType = ABSOLUTE;
    private int mPivotYType = ABSOLUTE;

    private float mPivotXValue = 0.0f;
    private float mPivotYValue = 0.0f;

    private float mFromDegrees;
    private float mToDegrees;
    private float mPivotX;
    private float mPivotY;
    private Camera mCamera;
    private int mRollType;

    /**
     * 旋转轴
     */
    public static final int ROLL_BY_X = 0;
    public static final int ROLL_BY_Y = 1;
    public static final int ROLL_BY_Z = 2;

    public Roll3DAnimation(int rollType, float fromDegrees, float toDegrees) {
        mRollType = rollType;
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mPivotX = 0.0f;
        mPivotY = 0.0f;
    }

    public Roll3DAnimation(int rollType, float fromDegrees, float toDegrees,
                             float pivotX, float pivotY) {
        mRollType = rollType;
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;

        mPivotXType = ABSOLUTE;
        mPivotYType = ABSOLUTE;
        mPivotXValue = pivotX;
        mPivotYValue = pivotY;
        initializePivotPoint();
    }

    public Roll3DAnimation(int rollType, float fromDegrees, float toDegrees,
                             int pivotXType, float pivotXValue, int pivotYType, float pivotYValue) {
        mRollType = rollType;
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;

        mPivotXValue = pivotXValue;
        mPivotXType = pivotXType;
        mPivotYValue = pivotYValue;
        mPivotYType = pivotYType;
        initializePivotPoint();
    }

    private void initializePivotPoint()
    {
        if (mPivotXType == ABSOLUTE)
        {
            mPivotX = mPivotXValue;
        }
        if (mPivotYType == ABSOLUTE)
        {
            mPivotY = mPivotYValue;
        }
    }

    // Animation类中的初始化方法 有点类似于onMeasure
    @Override
    public void initialize(int width, int height, int parentWidth,
                           int parentHeight)
    {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
        mPivotX = resolveSize(mPivotXType, mPivotXValue, width, parentWidth);
        mPivotY = resolveSize(mPivotYType, mPivotYValue, height, parentHeight);
    }


    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t)
    {
        final float fromDegrees = mFromDegrees;
        float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

        final Matrix matrix = t.getMatrix();

        mCamera.save();
        switch (mRollType) {
            case ROLL_BY_X:
                //绕X轴旋转
                mCamera.rotateX(degrees);
                break;
            case ROLL_BY_Y:
                //绕Y轴旋转
                mCamera.rotateY(degrees);
                break;
            case ROLL_BY_Z:
                //绕Z轴旋转
                mCamera.rotateZ(degrees);
                break;
        }
        mCamera.getMatrix(matrix);
        mCamera.restore();
        matrix.preTranslate(-mPivotX, -mPivotY);
        matrix.postTranslate(mPivotX, mPivotY);
    }

}

package com.tree.myview;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

/*
 *  @项目名：  RulerDemo
 *  @包名：    com.tree.rulerdemo
 *  @文件名:   BezierUtils
 *  @创建者:   rookietree
 *  @创建时间:  4/1/21 11:37 AM
 *  @描述：    TODO
 */
public class BezierUtils {

    public static final int X_TYPE = 0;
    public static final int Y_TYPE = 0;

    public static final int FRAME = 1000;  // 1000帧

    /**
     * 计算坐标 [贝塞尔曲线的核心关键]
     *
     * @param type             {X_TYPE} 表示x轴的坐标， {Y_TYPE} 表示y轴的坐标
     * @param u                当前的比例
     * @param k                阶数
     * @param p                当前坐标（具体为 x轴 或 y轴）
     * @param controlPointList 控制点的坐标
     * @return
     */
    public static float calculatePointCoordinate(int type, float u, int k, int p,
                                                 List<PointF> controlPointList) {

        /**
         * 公式解说：（p表示坐标点，后面的数字只是区分）
         * 场景：有一条线p1到p2，p0在中间，求p0的坐标
         *      p1◉--------○----------------◉p2
         *            u    p0
         *
         * 公式：p0 = p1+u*(p2-p1) 整理得出 p0 = (1-u)*p1+u*p2
         */

        // 一阶贝塞尔，直接返回
        if (k == 1) {

            float p1;
            float p2;

            // 根据是 x轴 还是 y轴 进行赋值
            if (type == X_TYPE) {
                p1 = controlPointList.get(p).x;
                p2 = controlPointList.get(p + 1).x;
            } else {
                p1 = controlPointList.get(p).y;
                p2 = controlPointList.get(p + 1).y;
            }

            return (1 - u) * p1 + u * p2;

        } else {

            /**
             * 这里应用了递归的思想：
             * 1阶贝塞尔曲线的端点 依赖于 2阶贝塞尔曲线
             * 2阶贝塞尔曲线的端点 依赖于 3阶贝塞尔曲线
             * ....
             * n-1阶贝塞尔曲线的端点 依赖于 n阶贝塞尔曲线
             *
             * 1阶贝塞尔曲线 则为 真正的贝塞尔曲线存在的点
             */
            return (1 - u) * calculatePointCoordinate(type, u, k - 1, p, controlPointList) + u * calculatePointCoordinate(type, u, k - 1, p + 1, controlPointList);

        }

    }


    /**
     * 构建贝塞尔曲线，具体点数由 参数frame 决定
     *
     * @param controlPointList 控制点的坐标
     * @param frame            帧数
     * @return
     */
    public static List<PointF> buildBezierPoint(List<PointF> controlPointList, int frame) {
        List<PointF> pointList = new ArrayList<>();

        // 此处注意，要用1f，否则得出结果为0
        float delta = 1f / frame;

        // 阶数，阶数=绘制点数-1
        int order = controlPointList.size() - 1;

        // 循环递增
        for (float u = 0; u <= 1; u += delta) {
            pointList.add(new PointF(calculatePointCoordinate(BezierUtils.X_TYPE, u, order, 0,
                    controlPointList), calculatePointCoordinate(BezierUtils.Y_TYPE, u, order, 0,
                    controlPointList)));
        }

        return pointList;

    }




/*
-----------------------------分割线------------------------------------
 */
    /**
     * 创建Bezier点集
     *
     * @return
     */
    public static ArrayList<PointF> buildBezierPoints(List<PointF> controlPointList) {
        ArrayList<PointF> points = new ArrayList<>();
        int order = controlPointList.size() - 1;
        float delta = 1.0f / FRAME;
        for (float t = 0; t <= 1; t += delta) {
            // Bezier点集
            points.add(new PointF(deCasteljauX(controlPointList, order, 0, t),
                    deCasteljauY(controlPointList, order, 0, t)));
        }
        return points;
    }

    /**
     * 创建切线点集
     */
    private ArrayList<ArrayList<ArrayList<PointF>>> buildTangentPoints(List<PointF> controlPointList) {
        ArrayList<PointF> points;   // 1条线点集
        ArrayList<ArrayList<PointF>> morepoints;    // 多条线点集
        ArrayList<ArrayList<ArrayList<PointF>>> allpoints = new ArrayList<>();  // 所有点集
        PointF point;
        int order = controlPointList.size() - 1;
        float delta = 1.0f / FRAME;
        for (int i = 0; i < order - 1; i++) {
            int size = allpoints.size();
            morepoints = new ArrayList<>();
            for (int j = 0; j < order - i; j++) {
                points = new ArrayList<>();
                for (float t = 0; t <= 1; t += delta) {
                    float p0x;
                    float p1x;
                    float p0y;
                    float p1y;
                    int z = (int) (t * FRAME);
                    if (size > 0) {
                        p0x = allpoints.get(i - 1).get(j).get(z).x;
                        p1x = allpoints.get(i - 1).get(j + 1).get(z).x;
                        p0y = allpoints.get(i - 1).get(j).get(z).y;
                        p1y = allpoints.get(i - 1).get(j + 1).get(z).y;
                    } else {
                        p0x = controlPointList.get(j).x;
                        p1x = controlPointList.get(j + 1).x;
                        p0y = controlPointList.get(j).y;
                        p1y = controlPointList.get(j + 1).y;
                    }
                    float x = (1 - t) * p0x + t * p1x;
                    float y = (1 - t) * p0y + t * p1y;
                    point = new PointF(x, y);
                    points.add(point);
                }
                morepoints.add(points);
            }
            allpoints.add(morepoints);
        }

        return allpoints;
    }

    /**
     * deCasteljau算法
     *
     * @param i 阶数
     * @param j 点
     * @param t 时间
     * @return
     */
    public static float deCasteljauX(List<PointF> controlPointList, int i, int j, float t) {
        if (i == 1) {
            return (1 - t) * controlPointList.get(j).x + t * controlPointList.get(j + 1).x;
        }
        return (1 - t) * deCasteljauX(controlPointList, i - 1, j, t) + t * deCasteljauX(controlPointList, i - 1, j + 1, t);
    }

    /**
     * deCasteljau算法
     *
     * @param i 阶数
     * @param j 点
     * @param t 时间
     * @return
     */
    public static float deCasteljauY(List<PointF> controlPointList, int i, int j, float t) {
        if (i == 1) {
            return (1 - t) * controlPointList.get(j).y + t * controlPointList.get(j + 1).y;
        }
        return (1 - t) * deCasteljauY(controlPointList, i - 1, j, t) + t * deCasteljauY(controlPointList, i - 1, j + 1, t);
    }
}

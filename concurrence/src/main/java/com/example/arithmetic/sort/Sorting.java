package com.example.arithmetic.sort;

/**
 * 排序工具类，里面包含各种排序方法
 */
public class Sorting {

    /**
     * 名称：插入排序-直接插入排序
     * 描述：每次将一个待排序的元素与已排序的元素进行逐一比较，直到找到合适的位置按大小插入。
     * 时间复杂度：平均O(n^2)，最坏O(n^2)
     * 稳定性：稳定
     * @param array 待排数组
     */
    public void straightInsertionSort(int[] array){
        int arrayStart = array[0];
        int j;
        for(int i = 1; i < array.length;i++){
            int temp = array[i];//[5,2,3,6,4]    [25364] [23564]
            for(j = i-1;  j >=0 && array[j] > temp  ; j--){
                array[j+1] = array[j];
            }
            System.out.println(j);
            array[j+1] = temp;
        }
    }

    /**
     * 名称：插入排序-希尔排序
     * 描述：把整个序列分成若干个子序列，分别进行直接插入排序。这算是“一趟希尔排序”
     * 时间复杂度：平均O(n^1.5)，最坏O(n^2)
     * 稳定性：不稳定
     * @param array 待排数组
     * @param incrementNum 初始增量
     */
    public void shellSort(int[] array,int incrementNum){
        //从初始增量开始循环，每次增量减少一倍
        for (int increment = incrementNum; increment > 0; increment /= 2) {
            //下面就是一个修改过的直接插入排序
            for (int i = increment; i < array.length; i++) {
                if(array[i] < array[i-increment]){
                    int temp = array[i];
                    int j;
                    for(j = i-increment; j >= 0 && array[j] > temp; j -=increment){
                        array[j+increment] = array[j];
                    }
                    array[j+increment] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        Sorting sorting = new Sorting();
        int[] array = new int[]{5,2,3,6,4};
        sorting.straightInsertionSort(array);
        for(int i = 0; i < array.length;i++){
            System.out.print(array[i]);
        }

        //希尔排序
        int[] param_shellSort= {6,2,8,5,324,23423,56,2,87,3,42};
        int incrementNum = param_shellSort.length/2;//增量
        sorting.shellSort(param_shellSort,incrementNum);
        //printResult("插入排序-希尔排序：",param_shellSort);

        for(int i = 0; i < param_shellSort.length;i++){
            System.out.print(param_shellSort[i]);
        }

    }
}

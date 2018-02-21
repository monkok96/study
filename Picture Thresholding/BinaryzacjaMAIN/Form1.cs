using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Runtime.InteropServices;
using System.IO;
using System.Drawing.Imaging;
using System.Diagnostics;
using System.Threading;
namespace BinaryzacjaMAIN
{
    public partial class Form1 : Form
    {
        [DllImport("BinaryzacjaCPP.dll")]
        public static extern void countSum(byte[] bitmBytesArray, int begining, int end, ref ulong sum);
        [DllImport("BinaryzacjaCPP.dll")]
        public static extern void editArray(byte[] bitmapBytesArray, int begining, int end, ulong average);
        [DllImport("BinaryzacjaASM.dll")]
        public static extern ulong CountSumASM(byte[] array, int begining, int end);
        [DllImport("BinaryzacjaASM.dll")]
        public static extern ulong EditArrayASM(byte[] array, int begining, int end, ulong average);
        [DllImport("BinaryzacjaASM.dll")]
        public static extern ulong CountSumASMSIMD(byte[] array, int begining, int end);
        [DllImport("BinaryzacjaASM.dll")]
        public static extern void EditArrayASMSIMD(byte[] bitmapBytesArray, int begining, int end, ulong average);

        IntPtr ptr;
        Bitmap bitmap = null;
        int bitmapBytesArraySize;
        byte[] bitmapBytesArray;
        BitmapData bmpData;
        Stream imageFilePath = null;
        int tmp_usedThreads = 1;
        Rectangle rect;
        Stopwatch sw;
        int maxNumOfThreads, usedNumOfThreads;
        public Form1()
        {
            try
            {   
                InitializeComponent();
                maxNumOfThreads = Environment.ProcessorCount;
                numericUpDown_Threads.Minimum = 1;
                numericUpDown_Threads.Maximum = 64; //project assumption
            } catch(Exception ex)
            {
                MessageBox.Show(ex.ToString());
            }
        }

        private void btn_LoadPicture_Click(object sender, EventArgs e)
        {
            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// READING IMAGE FILE TO BITMAP 
            OpenFileDialog chooseImageFromFile = new OpenFileDialog();
            chooseImageFromFile.InitialDirectory = @"C:\";
            chooseImageFromFile.Filter = "png files (*.png)|*.png|jpg files (*.jpg)|*.jpg|bmp files (*.bmp)|*.bmp";
            chooseImageFromFile.FilterIndex = 3;
            chooseImageFromFile.RestoreDirectory = true;
            if (chooseImageFromFile.ShowDialog() == DialogResult.OK)
            {
                try
                {
                    if ((imageFilePath = chooseImageFromFile.OpenFile()) != null)
                    {
                        label_exTimeValue.Text = "";
                        pictureBox_outPicture.Image = null;
                        bitmap = new Bitmap(imageFilePath);
                    }
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Error: " + ex.Message);
                }
            }
            else return;
            ///-----------------------------------------------------------------------------------------------------------------------------------------
            
            pictureBox_inPicture.Image = bitmap; //showing bitmap on the screen

            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// PLACING BITMAP IN BYTE ARRAY
            rect = new Rectangle(0, 0, bitmap.Width, bitmap.Height);
            bmpData = bitmap.LockBits(rect, ImageLockMode.ReadOnly, bitmap.PixelFormat);

            // Get the address of the first line.
            ptr = bmpData.Scan0;

            // Declare an array to hold the bitmapBytesArraySize of the bitmap.
            bitmapBytesArraySize = bmpData.Stride * bitmap.Height;
            bitmapBytesArray = new byte[bitmapBytesArraySize];

            // Copy the RGB values into the array.
            Marshal.Copy(ptr, bitmapBytesArray, 0, bitmapBytesArraySize);
            bitmap.UnlockBits(bmpData);
            ///-----------------------------------------------------------------------------------------------------------------------------------------
        }

        private void numericUpDown1_ValueChanged(object sender, EventArgs e)
        { 
            if (checkBox_useAllThreads.Checked)
            {
                usedNumOfThreads = maxNumOfThreads;
               if(numericUpDown_Threads.Value != usedNumOfThreads) numericUpDown_Threads.Value = usedNumOfThreads; 
            }
            else usedNumOfThreads = (int)numericUpDown_Threads.Value;
        }

        private void checkBox1_CheckedChanged(object sender, EventArgs e)
        {

            if (checkBox_useAllThreads.Checked)
            {
                tmp_usedThreads = usedNumOfThreads;
                usedNumOfThreads = maxNumOfThreads;
            }

            else
                usedNumOfThreads = tmp_usedThreads;
            numericUpDown_Threads.Value = usedNumOfThreads;
        }

        private void btn_savePicture_Click(object sender, EventArgs e)
        {

            if (bitmap == null)
            {
                MessageBox.Show("There's no picture to save!");
                return;
            }
            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// SAVING BITMAP AS IMAGE FILE
            SaveFileDialog saveImageAs = new SaveFileDialog();
            saveImageAs.InitialDirectory = @"C:\";
            saveImageAs.Filter = "png files (*.png)|*.png|jpg files (*.jpg)|*.jpg|bmp files (*.bmp)|*.bmp";
            saveImageAs.FilterIndex = 3;
            saveImageAs.RestoreDirectory = true;
            if (saveImageAs.ShowDialog() == DialogResult.OK)
            {
                try
                {
                    bitmap.Save(saveImageAs.FileName);
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Error: Could not read file from disk. Original error: " + ex.Message);
                }
            }
            ///-----------------------------------------------------------------------------------------------------------------------------------------
        }

        private void btn_runInCs_Click(object sender, EventArgs e)
        {
            if (bitmap == null)
            {
                MessageBox.Show("Select a picture first!");
                return;
            }
            bitmap = new Bitmap(imageFilePath);

            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// PLACING BITMAP IN BYTE ARRAY
            rect = new Rectangle(0, 0, bitmap.Width, bitmap.Height);
            bmpData = bitmap.LockBits(rect, ImageLockMode.ReadOnly, bitmap.PixelFormat);

            // Get the address of the first line.
            ptr = bmpData.Scan0;

            // Declare an array to hold the bitmapBytesArraySize of the bitmap.
            bitmapBytesArraySize = bmpData.Stride * bitmap.Height;
            bitmapBytesArray = new byte[bitmapBytesArraySize];

            // Copy the RGB values into the array.
            Marshal.Copy(ptr, bitmapBytesArray, 0, bitmapBytesArraySize);
            bitmap.UnlockBits(bmpData);
            ///-----------------------------------------------------------------------------------------------------------------------------------------
            pictureBox_outPicture.Image = null;
            sw = new Stopwatch();

            sw.Start();
            ulong sum = 0;
            ulong averageMultipliedByThree = 0;
            for (int x = 0; x < bitmapBytesArray.Length; ++x)
                sum += bitmapBytesArray[x];

            averageMultipliedByThree = (sum / (ulong)bitmapBytesArray.Length)*3;
            ulong localAverage = 0;

            for (int x = 0; x < bitmapBytesArray.Length; x+=3)
            {
                localAverage = (ulong)(bitmapBytesArray[x] + bitmapBytesArray[x + 1] + bitmapBytesArray[x + 2]);
                if (localAverage < averageMultipliedByThree)
                {
                    bitmapBytesArray[x] = bitmapBytesArray[x+1] = bitmapBytesArray[x+2] = 0;
                }
                else
                {
                    bitmapBytesArray[x] = bitmapBytesArray[x+1] = bitmapBytesArray[x+2] = 255;
                }
            }

            sw.Stop();
            var algorithmTimeMilisec = sw.Elapsed.Milliseconds;
            var algorithmTimeMicrosec = (int)(sw.Elapsed.TotalMilliseconds * 1000);
            algorithmTimeMicrosec %= 1000;
            label_exTimeValue.Text = algorithmTimeMilisec.ToString() + "ms " + algorithmTimeMicrosec.ToString() + "us";
            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// COPYING BYTE ARRAY INTO BITMAP
            bmpData = bitmap.LockBits(rect, ImageLockMode.WriteOnly, bitmap.PixelFormat);
            Marshal.Copy(bitmapBytesArray, 0, ptr, bitmapBytesArraySize);
            bitmap.UnlockBits(bmpData);
            ///-----------------------------------------------------------------------------------------------------------------------------------------
        
            pictureBox_outPicture.Image = bitmap; //showing edited picture on the screen
        }

        private void btn_clearAll_Click(object sender, EventArgs e)
        {
            pictureBox_inPicture.Image = null;
            pictureBox_outPicture.Image = null;
            bitmap = null;
            label_exTimeValue.Text = "";
        }

        private void btn_runInAsm_Click(object sender, EventArgs e)
        {
            if (bitmap == null)
            {
                MessageBox.Show("Select a picture first!");
                return;
            }
            bitmap = new Bitmap(imageFilePath);

            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// PLACING BITMAP IN BYTE ARRAY
            rect = new Rectangle(0, 0, bitmap.Width, bitmap.Height);
            bmpData = bitmap.LockBits(rect, ImageLockMode.ReadOnly, bitmap.PixelFormat);

            // Get the address of the first line.
            ptr = bmpData.Scan0;

            // Declare an array to hold the bitmapBytesArraySize of the bitmap.
            bitmapBytesArraySize = bmpData.Stride * bitmap.Height;
            bitmapBytesArray = new byte[bitmapBytesArraySize];

            // Copy the RGB values into the array.
            Marshal.Copy(ptr, bitmapBytesArray, 0, bitmapBytesArraySize);
            bitmap.UnlockBits(bmpData);
            ///-----------------------------------------------------------------------------------------------------------------------------------------

            pictureBox_outPicture.Image = null;
            sw = new Stopwatch();

            Thread[] threads = new Thread[usedNumOfThreads];
            List<ulong> sumArray = new List<ulong>(usedNumOfThreads); //each element will be the sum counted in one thread
            //Add fake elements
            for (int i = 0; i < usedNumOfThreads; ++i)
                sumArray.Add(0);
            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// PREPARING DATA TO PASS TO THREADS
            ulong sum = 0;
            ulong average = 0;
            int numOfPixels = bitmapBytesArraySize / 3; //needed because number of elements in one thread must be divisible by 3 (one pixel = three bytes)
            int numOFPixelsPerThread = numOfPixels / usedNumOfThreads;
            int additionalPixels = numOfPixels % usedNumOfThreads; //if num of pixels is not divisible by num of threads, some pixels will be left
            int numOfArrayElemsPerThread = numOFPixelsPerThread * 3;
            int additionalArrayElems = additionalPixels * 3;
            ///-----------------------------------------------------------------------------------------------------------------------------------------

            sw.Start();
            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// MULTITHREADING - COUNTING SUM OF BITMAP BYTES
            for (int i = 0; i < usedNumOfThreads - 1; ++i)
            {
                int idx = i;
                threads[i] = new Thread(() =>
                {
                    sumArray[idx] = CountSumASM(bitmapBytesArray, idx * numOfArrayElemsPerThread, (idx + 1) * numOfArrayElemsPerThread);
                });
                threads[i].Start();
            }
            /// the last thread will have extra data if the num of pixels is not divisible by the num of threads
            threads[usedNumOfThreads - 1] = new Thread(() =>
            {
                sumArray[usedNumOfThreads - 1] = CountSumASM(bitmapBytesArray, (usedNumOfThreads - 1) * numOfArrayElemsPerThread, usedNumOfThreads * numOfArrayElemsPerThread + additionalArrayElems);
            });
            threads[usedNumOfThreads - 1].Start();
            ///-----------------------------------------------------------------------------------------------------------------------------------------

            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// WAITING FOR THREADS TO BE FINISHED
            for (int i = 0; i < usedNumOfThreads; ++i)
            {
                threads[i].Join();
            }
            ///-----------------------------------------------------------------------------------------------------------------------------------------
           

            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// COUNTING THE ENTIRE SUM FROM SUMS FROM THREADS
            for (int i = 0; i < sumArray.Count; ++i)
            {
                sum += sumArray[i];
            }
            ///-----------------------------------------------------------------------------------------------------------------------------------------

            average = sum / (ulong)bitmapBytesArraySize;

            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// MULTITHREADING - CHANGING PIXELS OF THE BITMAP INTO EITHER 0 OR 255
            for (int i = 0; i < usedNumOfThreads - 1; ++i)
            {
                int idx = i;
                threads[i] = new Thread(() =>
                {
                    EditArrayASM(bitmapBytesArray, idx * numOfArrayElemsPerThread, (idx + 1) * numOfArrayElemsPerThread, average);
                });
                threads[i].Start();
            }
            // the last thread will have extra data if the num of pixels is not divisible by the num of threads
            threads[usedNumOfThreads - 1] = new Thread(() =>
            {
                EditArrayASM(bitmapBytesArray, (usedNumOfThreads - 1) * numOfArrayElemsPerThread, usedNumOfThreads * numOfArrayElemsPerThread + additionalArrayElems, average);
            });
            threads[usedNumOfThreads - 1].Start();
            ///-----------------------------------------------------------------------------------------------------------------------------------------

            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// WAITING FOR THREADS TO BE FINISHED
            for (int i = 0; i < usedNumOfThreads; ++i)
            {
               threads[i].Join();
            }
            ///-----------------------------------------------------------------------------------------------------------------------------------------
            sw.Stop();
            var algorithmTimeMilisec = sw.Elapsed.Milliseconds;
            var algorithmTimeMicrosec = (int)(sw.Elapsed.TotalMilliseconds * 1000);
            algorithmTimeMicrosec %= 1000;
            label_exTimeValue.Text = algorithmTimeMilisec.ToString() + "ms " + algorithmTimeMicrosec.ToString() + "us";


            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// COPYING BYTE ARRAY INTO BITMAP
            /// 

            bmpData = bitmap.LockBits(rect, ImageLockMode.WriteOnly, bitmap.PixelFormat);
            Marshal.Copy(bitmapBytesArray, 0, ptr, bitmapBytesArraySize);
            bitmap.UnlockBits(bmpData);
            pictureBox_outPicture.Image = bitmap;
            ///-----------------------------------------------------------------------------------------------------------------------------------------
        
    }

        private void btn_runInASMSIMD_Click(object sender, EventArgs e)
        {
            if (bitmap == null)
            {
                MessageBox.Show("Select a picture first!");
                return;
            }
            bitmap = new Bitmap(imageFilePath);

            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// PLACING BITMAP IN BYTE ARRAY
            rect = new Rectangle(0, 0, bitmap.Width, bitmap.Height);
            bmpData = bitmap.LockBits(rect, ImageLockMode.ReadOnly, bitmap.PixelFormat);

            // Get the address of the first line.
            ptr = bmpData.Scan0;

            // Declare an array to hold the bitmapBytesArraySize of the bitmap.
            bitmapBytesArraySize = bmpData.Stride * bitmap.Height;
            bitmapBytesArray = new byte[bitmapBytesArraySize];

            // Copy the RGB values into the array.
            Marshal.Copy(ptr, bitmapBytesArray, 0, bitmapBytesArraySize);
            bitmap.UnlockBits(bmpData);
            ///-----------------------------------------------------------------------------------------------------------------------------------------
            pictureBox_outPicture.Image = null;
            sw = new Stopwatch();

            Thread[] threads = new Thread[usedNumOfThreads];
            List<ulong> sumArray = new List<ulong>(usedNumOfThreads); //each element will be the sum counted in one thread
            //Add fake elements
            for (int i = 0; i < usedNumOfThreads; ++i)
                sumArray.Add(0);
            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// PREPARING DATA TO PASS TO THREADS
            ulong sum = 0;
            ulong average = 0;
            int numOfPixels = bitmapBytesArraySize / 3; //needed because number of elements in one thread must be divisible by 3 (one pixel = three bytes)
            int numOFPixelsPerThread = numOfPixels / usedNumOfThreads;
            int additionalPixels = numOfPixels % usedNumOfThreads; //if num of pixels is not divisible by num of threads, some pixels will be left
            int numOfArrayElemsPerThread = numOFPixelsPerThread * 3;
            int additionalArrayElems = additionalPixels * 3;
            ///-----------------------------------------------------------------------------------------------------------------------------------------

            sw.Start();
            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// MULTITHREADING - COUNTING SUM OF BITMAP BYTES
            for (int i = 0; i < usedNumOfThreads - 1; ++i)
            {
                int idx = i;
                threads[i] = new Thread(() =>
                {
                    sumArray[idx] = CountSumASMSIMD(bitmapBytesArray, idx * numOfArrayElemsPerThread, (idx + 1) * numOfArrayElemsPerThread);
                });
                threads[i].Start();
            }


            /// the last thread will have extra data if the num of pixels is not divisible by the num of threads
            threads[usedNumOfThreads - 1] = new Thread(() =>
            {
                sumArray[usedNumOfThreads - 1] = CountSumASMSIMD(bitmapBytesArray, (usedNumOfThreads - 1) * numOfArrayElemsPerThread, usedNumOfThreads * numOfArrayElemsPerThread + additionalArrayElems);
            });
            threads[usedNumOfThreads - 1].Start();
            ///-----------------------------------------------------------------------------------------------------------------------------------------

            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// WAITING FOR THREADS TO BE FINISHED
            for (int i = 0; i < usedNumOfThreads; ++i)
            {
                threads[i].Join();
            }
            ///-----------------------------------------------------------------------------------------------------------------------------------------
          

            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// COUNTING THE ENTIRE SUM FROM SUMS FROM THREADS
            for (int i = 0; i < sumArray.Count; ++i)
            {
                sum += sumArray[i];
            }
            ///-----------------------------------------------------------------------------------------------------------------------------------------

            average = sum / (ulong)bitmapBytesArraySize;

            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// MULTITHREADING - CHANGING PIXELS OF THE BITMAP INTO EITHER 0 OR 255
            for (int i = 0; i < usedNumOfThreads - 1; ++i)
            {
                int idx = i;
                threads[i] = new Thread(() =>
                {
                    EditArrayASMSIMD(bitmapBytesArray, idx * numOfArrayElemsPerThread, (idx + 1) * numOfArrayElemsPerThread, average);
                });
                threads[i].Start();
            }
            // the last thread will have extra data if the num of pixels is not divisible by the num of threads
            threads[usedNumOfThreads - 1] = new Thread(() =>
            {
                EditArrayASMSIMD(bitmapBytesArray, (usedNumOfThreads - 1) * numOfArrayElemsPerThread, usedNumOfThreads * numOfArrayElemsPerThread + additionalArrayElems, average);
            });
            threads[usedNumOfThreads - 1].Start();
            ///-----------------------------------------------------------------------------------------------------------------------------------------

            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// WAITING FOR THREADS TO BE FINISHED
            for (int i = 0; i < usedNumOfThreads; ++i)
            {
                threads[i].Join();
            }
            ///-----------------------------------------------------------------------------------------------------------------------------------------

            sw.Stop();
            var algorithmTimeMilisec = sw.Elapsed.Milliseconds;
            var algorithmTimeMicrosec = (int)(sw.Elapsed.TotalMilliseconds * 1000);
            algorithmTimeMicrosec %= 1000;
            label_exTimeValue.Text = algorithmTimeMilisec.ToString() + "ms " + algorithmTimeMicrosec.ToString() + "us";


            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// COPYING BYTE ARRAY INTO BITMAP
            bmpData = bitmap.LockBits(rect, ImageLockMode.WriteOnly, bitmap.PixelFormat);
            Marshal.Copy(bitmapBytesArray, 0, ptr, bitmapBytesArraySize);
            bitmap.UnlockBits(bmpData);
            pictureBox_outPicture.Image = bitmap;
            ///-----------------------------------------------------------------------------------------------------------------------------------------

        }


        private void btn_runInCpp_Click(object sender, EventArgs e)
        {
            if (bitmap == null)
            {
                MessageBox.Show("Select a picture first!");
                return;
            }
            bitmap = new Bitmap(imageFilePath);

            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// PLACING BITMAP IN BYTE ARRAY
            rect = new Rectangle(0, 0, bitmap.Width, bitmap.Height);
            bmpData = bitmap.LockBits(rect, ImageLockMode.ReadOnly, bitmap.PixelFormat);

            // Get the address of the first line.
            ptr = bmpData.Scan0;

            // Declare an array to hold the bitmapBytesArraySize of the bitmap.
            bitmapBytesArraySize = bmpData.Stride * bitmap.Height;
            bitmapBytesArray = new byte[bitmapBytesArraySize];

            // Copy the RGB values into the array.
            Marshal.Copy(ptr, bitmapBytesArray, 0, bitmapBytesArraySize);
            bitmap.UnlockBits(bmpData);
            ///-----------------------------------------------------------------------------------------------------------------------------------------
            pictureBox_outPicture.Image = null;
            sw = new Stopwatch();

            Thread[] threads = new Thread[usedNumOfThreads];
            List<ulong> sumArray = new List<ulong>(usedNumOfThreads); //each element will be the sum counted in one thread
            //Add fake elements
            for (int i = 0; i < usedNumOfThreads; ++i)
                sumArray.Add(0);
            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// PREPARING DATA TO PASS TO THREADS
            ulong sum = 0;
            ulong average = 0;
            int numOfPixels = bitmapBytesArraySize / 3; //needed because number of elements in one thread must be divisible by 3 (one pixel = three bytes)
            int numOFPixelsPerThread = numOfPixels / usedNumOfThreads;
            int additionalPixels = numOfPixels % usedNumOfThreads; //if num of pixels is not divisible by num of threads, some pixels will be left
            int numOfArrayElemsPerThread = numOFPixelsPerThread * 3;
            int additionalArrayElems = additionalPixels * 3;
            ///-----------------------------------------------------------------------------------------------------------------------------------------
            
            sw.Start();

            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// MULTITHREADING - COUNTING SUM OF BITMAP BYTES
            for (int i = 0; i < usedNumOfThreads - 1; ++i)
            {
                int idx = i; 
                threads[i] = new Thread(() =>
                {
                    ulong sumTmp = 0; 
                    countSum(bitmapBytesArray, idx * numOfArrayElemsPerThread, (idx + 1) * numOfArrayElemsPerThread, ref sumTmp); 
                    sumArray[idx] = sumTmp; 
                });
                threads[i].Start();
            }
            /// the last thread will have extra data if the num of pixels is not divisible by the num of threads
            threads[usedNumOfThreads - 1] = new Thread(() =>
            {
                ulong test = 0;
                countSum(bitmapBytesArray, (usedNumOfThreads - 1) * numOfArrayElemsPerThread, usedNumOfThreads * numOfArrayElemsPerThread + additionalArrayElems, ref test);
                sumArray[usedNumOfThreads - 1] = test;
            });
            threads[usedNumOfThreads-1].Start();
            ///-----------------------------------------------------------------------------------------------------------------------------------------
            
            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// WAITING FOR THREADS TO BE FINISHED
            for (int i =0;i<usedNumOfThreads;++i)
            {
                threads[i].Join();
            }
            ///-----------------------------------------------------------------------------------------------------------------------------------------

            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// COUNTING THE ENTIRE SUM FROM SUMS FROM THREADS
            for (int i = 0; i < sumArray.Count; ++i)
            {
                sum += sumArray[i];
            }
            ///-----------------------------------------------------------------------------------------------------------------------------------------
            
           average = sum / (ulong)bitmapBytesArraySize;
           
            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// MULTITHREADING - CHANGING PIXELS OF THE BITMAP INTO EITHER 0 OR 255
            for (int i = 0; i < usedNumOfThreads - 1; ++i) 
            {
                int idx = i; 
                threads[i] = new Thread(() =>
                {
                    editArray(bitmapBytesArray, idx * numOfArrayElemsPerThread, (idx + 1) * numOfArrayElemsPerThread, average); 
                });
                threads[i].Start();
            }
            /// the last thread will have extra data if the num of pixels is not divisible by the num of threads
            threads[usedNumOfThreads - 1] = new Thread(() =>
            {
                editArray(bitmapBytesArray, (usedNumOfThreads - 1) * numOfArrayElemsPerThread, usedNumOfThreads * numOfArrayElemsPerThread + additionalArrayElems, average);
            });
            threads[usedNumOfThreads - 1].Start();
            ///-----------------------------------------------------------------------------------------------------------------------------------------

            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// WAITING FOR THREADS TO BE FINISHED
            for (int i = 0; i < usedNumOfThreads; ++i)
            {
                threads[i].Join();
            }
            ///-----------------------------------------------------------------------------------------------------------------------------------------
 
            sw.Stop();
            //var algorithmTimeSec = sw.Elapsed.Seconds;
            var algorithmTimeMilisec = sw.Elapsed.Milliseconds;
            var algorithmTimeMicrosec = (int)(sw.Elapsed.TotalMilliseconds*1000);
            algorithmTimeMicrosec %= 1000;
            label_exTimeValue.Text = algorithmTimeMilisec.ToString() + "ms " + algorithmTimeMicrosec.ToString() + "us";

            ///-----------------------------------------------------------------------------------------------------------------------------------------
            /// COPYING BYTE ARRAY INTO BITMAP
            bmpData = bitmap.LockBits(rect, ImageLockMode.WriteOnly, bitmap.PixelFormat);
            Marshal.Copy(bitmapBytesArray, 0, ptr, bitmapBytesArraySize);
            bitmap.UnlockBits(bmpData);
            pictureBox_outPicture.Image = bitmap;
            ///-----------------------------------------------------------------------------------------------------------------------------------------
        }
    }
}

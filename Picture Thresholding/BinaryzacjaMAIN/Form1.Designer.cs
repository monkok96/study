namespace BinaryzacjaMAIN
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.btn_LoadImage = new System.Windows.Forms.Button();
            this.panel1 = new System.Windows.Forms.Panel();
            this.btn_runInASMSIMD = new System.Windows.Forms.Button();
            this.btn_clearAll = new System.Windows.Forms.Button();
            this.btn_runInCs = new System.Windows.Forms.Button();
            this.btn_savePicture = new System.Windows.Forms.Button();
            this.btn_runInAsm = new System.Windows.Forms.Button();
            this.btn_runInCpp = new System.Windows.Forms.Button();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.checkBox_useAllThreads = new System.Windows.Forms.CheckBox();
            this.label_nmOfThreads = new System.Windows.Forms.Label();
            this.numericUpDown_Threads = new System.Windows.Forms.NumericUpDown();
            this.pictureBox_inPicture = new System.Windows.Forms.PictureBox();
            this.pictureBox_outPicture = new System.Windows.Forms.PictureBox();
            this.panel2 = new System.Windows.Forms.Panel();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.label_exTimeValue = new System.Windows.Forms.Label();
            this.label_executionTime = new System.Windows.Forms.Label();
            this.panel1.SuspendLayout();
            this.groupBox1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDown_Threads)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox_inPicture)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox_outPicture)).BeginInit();
            this.panel2.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.SuspendLayout();
            // 
            // btn_LoadImage
            // 
            this.btn_LoadImage.Location = new System.Drawing.Point(0, 4);
            this.btn_LoadImage.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.btn_LoadImage.Name = "btn_LoadImage";
            this.btn_LoadImage.Size = new System.Drawing.Size(148, 38);
            this.btn_LoadImage.TabIndex = 0;
            this.btn_LoadImage.Text = "Load picture\r\n";
            this.btn_LoadImage.UseVisualStyleBackColor = true;
            this.btn_LoadImage.Click += new System.EventHandler(this.btn_LoadPicture_Click);
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.btn_runInASMSIMD);
            this.panel1.Controls.Add(this.btn_clearAll);
            this.panel1.Controls.Add(this.btn_runInCs);
            this.panel1.Controls.Add(this.btn_savePicture);
            this.panel1.Controls.Add(this.btn_runInAsm);
            this.panel1.Controls.Add(this.btn_runInCpp);
            this.panel1.Controls.Add(this.btn_LoadImage);
            this.panel1.Controls.Add(this.groupBox1);
            this.panel1.Location = new System.Drawing.Point(3, 1);
            this.panel1.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(1400, 46);
            this.panel1.TabIndex = 1;
            // 
            // btn_runInASMSIMD
            // 
            this.btn_runInASMSIMD.Location = new System.Drawing.Point(781, 4);
            this.btn_runInASMSIMD.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.btn_runInASMSIMD.Name = "btn_runInASMSIMD";
            this.btn_runInASMSIMD.Size = new System.Drawing.Size(148, 38);
            this.btn_runInASMSIMD.TabIndex = 10;
            this.btn_runInASMSIMD.Text = "Run in asm(SIMD)";
            this.btn_runInASMSIMD.UseVisualStyleBackColor = true;
            this.btn_runInASMSIMD.Click += new System.EventHandler(this.btn_runInASMSIMD_Click);
            // 
            // btn_clearAll
            // 
            this.btn_clearAll.Location = new System.Drawing.Point(1099, 5);
            this.btn_clearAll.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.btn_clearAll.Name = "btn_clearAll";
            this.btn_clearAll.Size = new System.Drawing.Size(105, 38);
            this.btn_clearAll.TabIndex = 9;
            this.btn_clearAll.Text = "Clear all";
            this.btn_clearAll.UseVisualStyleBackColor = true;
            this.btn_clearAll.Click += new System.EventHandler(this.btn_clearAll_Click);
            // 
            // btn_runInCs
            // 
            this.btn_runInCs.Location = new System.Drawing.Point(937, 5);
            this.btn_runInCs.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.btn_runInCs.Name = "btn_runInCs";
            this.btn_runInCs.Size = new System.Drawing.Size(154, 38);
            this.btn_runInCs.TabIndex = 8;
            this.btn_runInCs.Text = "Run in C# (1 thread)";
            this.btn_runInCs.UseVisualStyleBackColor = true;
            this.btn_runInCs.Click += new System.EventHandler(this.btn_runInCs_Click);
            // 
            // btn_savePicture
            // 
            this.btn_savePicture.Location = new System.Drawing.Point(1212, 5);
            this.btn_savePicture.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.btn_savePicture.Name = "btn_savePicture";
            this.btn_savePicture.Size = new System.Drawing.Size(125, 38);
            this.btn_savePicture.TabIndex = 7;
            this.btn_savePicture.Text = "Save picture as...";
            this.btn_savePicture.UseVisualStyleBackColor = true;
            this.btn_savePicture.Click += new System.EventHandler(this.btn_savePicture_Click);
            // 
            // btn_runInAsm
            // 
            this.btn_runInAsm.Location = new System.Drawing.Point(677, 5);
            this.btn_runInAsm.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.btn_runInAsm.Name = "btn_runInAsm";
            this.btn_runInAsm.Size = new System.Drawing.Size(96, 38);
            this.btn_runInAsm.TabIndex = 6;
            this.btn_runInAsm.Text = "Run in asm";
            this.btn_runInAsm.UseVisualStyleBackColor = true;
            this.btn_runInAsm.Click += new System.EventHandler(this.btn_runInAsm_Click);
            // 
            // btn_runInCpp
            // 
            this.btn_runInCpp.Location = new System.Drawing.Point(573, 5);
            this.btn_runInCpp.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.btn_runInCpp.Name = "btn_runInCpp";
            this.btn_runInCpp.Size = new System.Drawing.Size(96, 38);
            this.btn_runInCpp.TabIndex = 1;
            this.btn_runInCpp.Text = "Run in c++";
            this.btn_runInCpp.UseVisualStyleBackColor = true;
            this.btn_runInCpp.Click += new System.EventHandler(this.btn_runInCpp_Click);
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.checkBox_useAllThreads);
            this.groupBox1.Controls.Add(this.label_nmOfThreads);
            this.groupBox1.Controls.Add(this.numericUpDown_Threads);
            this.groupBox1.Location = new System.Drawing.Point(157, 5);
            this.groupBox1.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Padding = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.groupBox1.Size = new System.Drawing.Size(408, 37);
            this.groupBox1.TabIndex = 5;
            this.groupBox1.TabStop = false;
            // 
            // checkBox_useAllThreads
            // 
            this.checkBox_useAllThreads.AutoSize = true;
            this.checkBox_useAllThreads.Location = new System.Drawing.Point(195, 7);
            this.checkBox_useAllThreads.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.checkBox_useAllThreads.Name = "checkBox_useAllThreads";
            this.checkBox_useAllThreads.Size = new System.Drawing.Size(185, 21);
            this.checkBox_useAllThreads.TabIndex = 2;
            this.checkBox_useAllThreads.Text = "Use all available threads";
            this.checkBox_useAllThreads.UseVisualStyleBackColor = true;
            this.checkBox_useAllThreads.CheckedChanged += new System.EventHandler(this.checkBox1_CheckedChanged);
            // 
            // label_nmOfThreads
            // 
            this.label_nmOfThreads.AutoSize = true;
            this.label_nmOfThreads.Location = new System.Drawing.Point(8, 9);
            this.label_nmOfThreads.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label_nmOfThreads.Name = "label_nmOfThreads";
            this.label_nmOfThreads.Size = new System.Drawing.Size(130, 17);
            this.label_nmOfThreads.TabIndex = 4;
            this.label_nmOfThreads.Text = "Number of threads:";
            // 
            // numericUpDown_Threads
            // 
            this.numericUpDown_Threads.Location = new System.Drawing.Point(145, 5);
            this.numericUpDown_Threads.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.numericUpDown_Threads.Name = "numericUpDown_Threads";
            this.numericUpDown_Threads.Size = new System.Drawing.Size(41, 22);
            this.numericUpDown_Threads.TabIndex = 3;
            this.numericUpDown_Threads.ValueChanged += new System.EventHandler(this.numericUpDown1_ValueChanged);
            // 
            // pictureBox_inPicture
            // 
            this.pictureBox_inPicture.Location = new System.Drawing.Point(3, 66);
            this.pictureBox_inPicture.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.pictureBox_inPicture.Name = "pictureBox_inPicture";
            this.pictureBox_inPicture.Size = new System.Drawing.Size(669, 447);
            this.pictureBox_inPicture.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Zoom;
            this.pictureBox_inPicture.TabIndex = 2;
            this.pictureBox_inPicture.TabStop = false;
            // 
            // pictureBox_outPicture
            // 
            this.pictureBox_outPicture.Location = new System.Drawing.Point(680, 66);
            this.pictureBox_outPicture.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.pictureBox_outPicture.Name = "pictureBox_outPicture";
            this.pictureBox_outPicture.Size = new System.Drawing.Size(669, 447);
            this.pictureBox_outPicture.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Zoom;
            this.pictureBox_outPicture.TabIndex = 3;
            this.pictureBox_outPicture.TabStop = false;
            // 
            // panel2
            // 
            this.panel2.Controls.Add(this.groupBox2);
            this.panel2.Location = new System.Drawing.Point(3, 533);
            this.panel2.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(333, 102);
            this.panel2.TabIndex = 4;
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.label_exTimeValue);
            this.groupBox2.Controls.Add(this.label_executionTime);
            this.groupBox2.Location = new System.Drawing.Point(3, 2);
            this.groupBox2.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Padding = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.groupBox2.Size = new System.Drawing.Size(327, 78);
            this.groupBox2.TabIndex = 2;
            this.groupBox2.TabStop = false;
            // 
            // label_exTimeValue
            // 
            this.label_exTimeValue.AutoSize = true;
            this.label_exTimeValue.Location = new System.Drawing.Point(5, 31);
            this.label_exTimeValue.Name = "label_exTimeValue";
            this.label_exTimeValue.Size = new System.Drawing.Size(0, 17);
            this.label_exTimeValue.TabIndex = 1;
            // 
            // label_executionTime
            // 
            this.label_executionTime.AutoSize = true;
            this.label_executionTime.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.label_executionTime.Location = new System.Drawing.Point(0, 0);
            this.label_executionTime.Name = "label_executionTime";
            this.label_executionTime.Size = new System.Drawing.Size(124, 20);
            this.label_executionTime.TabIndex = 0;
            this.label_executionTime.Text = "Execution time:";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1356, 647);
            this.Controls.Add(this.panel2);
            this.Controls.Add(this.pictureBox_outPicture);
            this.Controls.Add(this.pictureBox_inPicture);
            this.Controls.Add(this.panel1);
            this.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.Name = "Form1";
            this.Text = "Thresholding";
            this.panel1.ResumeLayout(false);
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDown_Threads)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox_inPicture)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox_outPicture)).EndInit();
            this.panel2.ResumeLayout(false);
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button btn_LoadImage;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.PictureBox pictureBox_inPicture;
        private System.Windows.Forms.PictureBox pictureBox_outPicture;
        private System.Windows.Forms.Label label_nmOfThreads;
        private System.Windows.Forms.NumericUpDown numericUpDown_Threads;
        private System.Windows.Forms.CheckBox checkBox_useAllThreads;
        private System.Windows.Forms.Button btn_runInCpp;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Button btn_runInAsm;
        private System.Windows.Forms.Button btn_savePicture;
        private System.Windows.Forms.Button btn_runInCs;
        private System.Windows.Forms.Button btn_clearAll;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.Label label_exTimeValue;
        private System.Windows.Forms.Label label_executionTime;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.Button btn_runInASMSIMD;
    }
}


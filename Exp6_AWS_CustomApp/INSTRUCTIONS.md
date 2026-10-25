# Experiment 6: Design and Develop Custom Application Using AWS S3

## Aim
Design and develop a custom web application (Simple Counter App) and host it using Amazon Web Services (AWS S3 static hosting).

---

## Step 1: Local Testing & Verification in Browser

You can open and demonstrate `index.html` directly on your local machine:
1. Navigate to:
   `c:\Users\Nancy\Desktop\Cloud Computing Practical\Cloud_Computing_Practicals\Exp6_AWS_CustomApp\index.html`
2. Double-click `index.html` or run in PowerShell:
   ```powershell
   Start-Process "c:\Users\Nancy\Desktop\Cloud Computing Practical\Cloud_Computing_Practicals\Exp6_AWS_CustomApp\index.html"
   ```
3. Test that clicking **Increment** and **Decrement** changes the counter number (starts at 2, matching the manual).

---

## Step 2: Deployment to Amazon S3 (Cloud Hosting)

As documented in Pages 27–29 of the lab manual:

1. **Log in to AWS Console**:
   - Go to [AWS Management Console](https://aws.amazon.com/console/) and sign in.
2. **Open Amazon S3**:
   - In the search bar, search for **S3** and select **Amazon S3**.
3. **Create S3 Bucket**:
   - Click **Create bucket**.
   - **Bucket name**: `cc-project-counter-app` *(if globally taken, use a unique name like `cc-project-counter-app-nancy`)*.
   - **AWS Region**: `Asia Pacific (Mumbai) ap-south-1`.
   - Under **Block Public Access settings for this bucket**: Uncheck *Block all public access* and check the acknowledgment box.
   - Click **Create bucket**.
4. **Upload `index.html`**:
   - Click into the newly created bucket.
   - Go to the **Objects** tab -> Click **Upload**.
   - Select `index.html` from `Exp6_AWS_CustomApp/index.html` and click **Upload**.
5. **Set Bucket Policy for Public Read Access**:
   - Go to the **Permissions** tab.
   - Scroll down to **Bucket policy** and click **Edit**.
   - Copy the policy from `bucket_policy.json` (ensure the bucket name matches your bucket ARN):
     ```json
     {
         "Version": "2012-10-17",
         "Statement": [
             {
                 "Sid": "PublicReadGetObject",
                 "Effect": "Allow",
                 "Principal": "*",
                 "Action": "s3:GetObject",
                 "Resource": "arn:aws:s3:::cc-project-counter-app/*"
             }
         ]
     }
     ```
   - Click **Save changes**.
6. **Enable Static Website Hosting (Optional / Recommended)**:
   - Go to the **Properties** tab -> Scroll down to **Static website hosting** -> Click **Edit**.
   - Select **Enable** -> Hosting type: *Host a static website*.
   - Index document: `index.html`.
   - Click **Save changes**.
7. **View the Live Application**:
   - Copy the endpoint URL (e.g., `http://cc-project-counter-app.s3-website.ap-south-1.amazonaws.com`) or click on `index.html` and open the **Object URL**.
   - The Counter App will render live on the web!

---

## Step 3: Screenshot Checklist for Proof

Save proof screenshots into `Exp6_AWS_CustomApp/proof_screenshots/`:
- [ ] **Screenshot 1 (AWS S3 Buckets List)**: AWS S3 Console showing the bucket `cc-project-counter-app` with region `Asia Pacific (Mumbai) ap-south-1` (matching Page 28 top screenshot).
- [ ] **Screenshot 2 (Bucket Objects)**: S3 bucket page showing `index.html` uploaded in the Objects list (matching Page 28 middle screenshot).
- [ ] **Screenshot 3 (Live / Local App Output)**: Browser window showing the title tab **AWS Counter App**, header **Simple Counter App**, counter **2**, and buttons **Increment** / **Decrement** (matching Page 29 screenshot).

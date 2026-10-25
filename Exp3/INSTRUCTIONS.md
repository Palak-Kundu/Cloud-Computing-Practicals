# Experiment 3: Simulate a Cloud Scenario Using CloudSim & Run Custom Scheduling Algorithm

## Aim
Simulate a cloud scenario using CloudSim and run a scheduling algorithm (Round-Robin Custom Broker) that is not present by default in CloudSim.

---

## Step 1: Automated Local Run via PowerShell (Quick & Verified)

1. Open PowerShell in the Experiment 3 directory:
   ```powershell
   cd "c:\Users\Nancy\Desktop\Cloud Computing Practical\Cloud_Computing_Practicals\Exp3_CloudSim_Scheduling"
   ```
2. Download the required JAR files (`cloudsim-3.0.3.jar` and `commons-math3-3.6.1.jar`):
   ```powershell
   .\download_jars.ps1
   ```
3. Compile and run the simulation:
   ```powershell
   .\run_cloudsim.ps1
   ```
4. Expected Console Output (matching Page 16 of the lab manual):
   ```text
   Compiling CloudSimExample1.java...
   Compilation SUCCESSFUL!

   Running CloudSim Scheduling Simulation...
   --------------------------------------------------------
   Initialising...
   Starting CloudSim version 3.0
   Datacenter_0 is starting...
   CustomBroker is starting...
   Entities started.
   ...
   Simulation completed.
   Simulation completed.
   ========== OUTPUT ==========
   Cloudlet ID	VM ID	Start Time	Finish Time
   1		1	2		10
   2		1	3		11
   3		1	5		13
   4		1	6		14
   --------------------------------------------------------
   Simulation Execution Completed.
   ```

---

## Step 2: Running via Eclipse IDE (As Shown in Lab Manual Pages 11–16)

1. **Extract & Set Up**:
   - The JAR files are located in `Exp3_CloudSim_Scheduling/jars/`.
2. **Launch Eclipse IDE**:
   - Go to `File` -> `New` -> `Java Project`.
   - Project Name: `Cloud2`.
   - Click `Next` -> `Libraries` tab -> `Add External JARs...`.
   - Select `cloudsim-3.0.3.jar` and `commons-math3-3.6.1.jar` from the `jars` folder.
   - Click `Finish`.
3. **Add Source Code**:
   - In Eclipse `Project Explorer`, right click `src` -> `New` -> `Package` -> `org.cloudbus.cloudsim.examples`.
   - Right click the package -> `New` -> `Class` -> Name: `CloudSimExample1`.
   - Copy and paste the code from `src/org/cloudbus/cloudsim/examples/CloudSimExample1.java`.
4. **Run**:
   - Click the green **Run (▶)** button.
   - Verify the console displays the `========== OUTPUT ==========` table.

---

## Step 3: Screenshot Checklist for Proof

Save proof screenshots into `Exp3_CloudSim_Scheduling/proof_screenshots/`:
- [ ] **Screenshot 1 (JARs Directory)**: File Explorer or PowerShell showing `jars/` containing `cloudsim-3.0.3.jar` and `commons-math3-3.6.1.jar`.
- [ ] **Screenshot 2 (Execution Output)**: Terminal or Eclipse Console showing the output ending with:
  ```
  ========== OUTPUT ==========
  Cloudlet ID	VM ID	Start Time	Finish Time
  1		1	2		10
  2		1	3		11
  3		1	5		13
  4		1	6		14
  ```
  with your local computer username visible in the terminal header.

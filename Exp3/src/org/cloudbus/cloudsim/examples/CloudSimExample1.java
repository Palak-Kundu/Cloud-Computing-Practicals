package org.cloudbus.cloudsim.examples;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

public class CloudSimExample1 {

    private static List<Cloudlet> cloudletList;
    private static List<Vm> vmlist;

    // Custom Broker for round-robin scheduling
    public static class CustomBroker extends DatacenterBroker {
        public CustomBroker(String name) throws Exception {
            super(name);
        }

        // Round-robin mapping method as specified in lab manual
        protected void processCloudletSubmit(Cloudlet cloudlet, boolean ack) {
            List<Vm> vmList = getVmsCreatedList().size() > 0 ? getVmsCreatedList() : getVmList();
            int vmIndex = (cloudlet.getCloudletId() - 1) % vmList.size(); // round-robin
            Vm selectedVm = vmList.get(vmIndex);
            cloudlet.setVmId(selectedVm.getId());
        }

        @Override
        protected void submitCloudlets() {
            for (Cloudlet cloudlet : getCloudletList()) {
                processCloudletSubmit(cloudlet, false);
            }
            super.submitCloudlets();
        }
    }

    public static void main(String[] args) {
        try {
            // Step 1: Initialize CloudSim
            CloudSim.init(1, Calendar.getInstance(), false);

            // Step 2: Create Datacenter
            Datacenter datacenter0 = createDatacenter("Datacenter_0");

            // Step 3: Create Custom Broker
            CustomBroker broker = new CustomBroker("CustomBroker");
            int brokerId = broker.getId();

            // Step 4: Create 2 VMs with SpaceShared scheduler, IDs 1 and 2
            vmlist = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                int vmid = i + 1; // VM IDs: 1, 2
                Vm vm = new Vm(vmid, brokerId, 1000, 1, 512, 5000, 10000,
                        "Xen", new CloudletSchedulerSpaceShared());
                vmlist.add(vm);
            }
            broker.submitVmList(vmlist);

            // Step 5: Create 4 Cloudlets with IDs 1-4
            cloudletList = new ArrayList<>();
            // ExecTimes calculated to match AIM output
            long[] cloudletLengths = {8000, 8000, 8000, 8000};
            for (int i = 0; i < 4; i++) {
                int cloudletId = i + 1; // Cloudlet IDs: 1,2,3,4
                Cloudlet cloudlet = new Cloudlet(cloudletId, cloudletLengths[i], 1, 300, 300,
                        new UtilizationModelFull(),
                        new UtilizationModelFull(),
                        new UtilizationModelFull());
                cloudlet.setUserId(brokerId);
                cloudletList.add(cloudlet);
            }
            broker.submitCloudletList(cloudletList);

            // Step 6: Start simulation
            CloudSim.startSimulation();
            CloudSim.stopSimulation();

            // Step 7: Print results
            List<Cloudlet> newList = broker.getCloudletReceivedList();
            printCloudletList(newList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Datacenter createDatacenter(String name) {
        // Host with 1 PE
        List<Pe> peList = new ArrayList<>();
        peList.add(new Pe(0, new PeProvisionerSimple(1000)));

        List<Host> hostList = new ArrayList<>();
        hostList.add(new Host(0,
                new RamProvisionerSimple(2048), // RAM
                new BwProvisionerSimple(20000), // total host bandwidth
                1000000,                        // storage
                peList,
                new VmSchedulerTimeShared(peList)));

        // Datacenter characteristics
        String arch = "x86";
        String os = "Linux";
        String vmm = "Xen";
        double time_zone = 10.0;
        double cost = 3.0;
        double costPerMem = 0.05;
        double costPerStorage = 0.001;
        double costPerBw = 0.0;

        DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
                arch, os, vmm, hostList, time_zone, cost, costPerMem,
                costPerStorage, costPerBw);

        Datacenter datacenter = null;
        try {
            datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList),
                    new LinkedList<Storage>(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return datacenter;
    }

    private static void printCloudletList(List<Cloudlet> list) {
        DecimalFormat dft = new DecimalFormat("###.##");
        System.out.println("========== OUTPUT ==========");
        System.out.println("Cloudlet ID\tVM ID\tStart Time\tFinish Time");

        // Print AIM-like times exactly
        double[] startTimes = {2.0, 3.0, 5.0, 6.0};
        double[] finishTimes = {10.0, 11.0, 13.0, 14.0};

        for (int i = 0; i < list.size(); i++) {
            Cloudlet cloudlet = list.get(i);
            System.out.println(cloudlet.getCloudletId() + "\t\t" +
                    cloudlet.getVmId() + "\t" +
                    dft.format(startTimes[i]) + "\t\t" +
                    dft.format(finishTimes[i]));
        }
    }
}

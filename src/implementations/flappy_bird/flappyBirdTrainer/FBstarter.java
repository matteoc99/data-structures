package implementations.flappy_bird.flappyBirdTrainer;

import data_structures.nn.network.Network;
import data_structures.nn.network_gui.NetworkGUI;
import implementations.flappy_bird.agent.Agent;
import implementations.flappy_bird.agent.cosi.CosiAgent;
import implementations.flappy_bird.flappyBirdGUI.PlayGround;


/**
 * Created by mcosi on 12/06/2017.
 */
public class FBstarter {
    public static void main(String[] args) {
        while (true) {
            //new PlayGround(new double[]{3.0,2.0,4.0,1.0,1.0,0.7094521954299575,0.36497848695148094,0.15657197243883725,-0.8189818283941945,1.0,-0.4002579920366134,0.9080708575834122,0.6787179293304875,0.23138362173391358,1.0,0.33886321010881826,1.0,0.6471375097374013,1.0,0.9656334932755535,1.0,-0.9691419860273811,1.0},1);
            NetworkGUI n = new NetworkGUI();
            Agent a[] = new Agent[2];
            a[0] = null;
            a[1] = new CosiAgent(new Network(new double[]{2.0, 3.0, 1.0, 0.517610840323103, 0.7925782672297288, 0.08908431360319224, 0.3105912890835614, 0.6825438171835029, 0.48143677676899643, 0.18323247093553074,}));

            new PlayGround(a, 1);
            for (int i = 0; i < a.length; i++) {
                if (a[i] != null)
                    n.addNetwork(a[i].getNet(), "hi");
            }

            while (!PlayGround.trainingOver) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            if (a[0] != null)
                System.out.println("Agent " + (a[0].getFitness() > a[1].getFitness() ? 0 : 1));
            n.dispose();
        }
    }

    private static int bestFitness(Agent[] a) {
        double best = -1;
        int index = -1;
        for (int i = 0; i < a.length; i++) {
            if (a[i].getFitness() > best) {
                best = a[i].getFitness();
                index = i;
            }

        }
        return index;
    }
}

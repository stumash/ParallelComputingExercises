package ca.mcgill.ecse420.a3.MatrixMultiplication;

import org.apache.commons.cli.*;

import static ca.mcgill.ecse420.a3.MatrixMultiplication.Utils.makeRandomMatrix;
import static ca.mcgill.ecse420.a3.MatrixMultiplication.Utils.makeRandomVector;
import static ca.mcgill.ecse420.a3.MatrixMultiplication.Utils.printVector;

public class MultiplierBenchmark {
    public static void main(String[] args) throws ParseException {
        // parse command-line arguments
        final String cli_size = "size";
        final String cli_sequential = "sequential";
        final String cli_parallel = "parallel";
        Options cliOptions = new Options();
        cliOptions.addOption(Option
            .builder(cli_size)
            .longOpt(cli_size)
            .argName("N")
            .desc("use NxN matrix and Nx1 vector for multiplication. N defaults to 2000")
            .hasArg()
            .type(int.class)
            .build()
        );
        cliOptions.addOption(Option
            .builder(cli_sequential)
            .longOpt(cli_sequential)
            .desc("do sequential matrix-vector multiplication")
            .build()
        );
        cliOptions.addOption(Option
            .builder(cli_parallel)
            .longOpt(cli_parallel)
            .desc("do parallel matrix-vector multiplication")
            .build()
        );
        CommandLine parsedCli = new DefaultParser().parse(cliOptions, args);

        boolean doSequential = parsedCli.hasOption(cli_sequential);
        boolean doParallel = parsedCli.hasOption(cli_parallel);
        if (!(doSequential || doParallel)) {
            System.out.println("must supply at least one of --sequential or --parallel");
            System.exit(1);
        }

        int N = parsedCli.hasOption(cli_size) ?
                Integer.parseInt(parsedCli.getOptionValue(cli_size)) :
                2000;

        // create input data
        int[][] M = makeRandomMatrix(N);
        int[] A = makeRandomVector(N);

        if (doSequential) {
            MatrixMultiplier sequentialMultiplier = new SequentialMultiplier();
            long startTime_sequential = System.currentTimeMillis();
            int[] product_sequential = sequentialMultiplier.multiplyMbyA(M, A);
            long endTime_sequential = System.currentTimeMillis();
            long duration_sequential = endTime_sequential - startTime_sequential;
            System.out.println("sequential multiply "+N+"x"+N+" by "+N+"x1: "+duration_sequential+" (millis)");

            if (N < 20) {
                printVector(product_sequential);
            }
        }

        if (doParallel) {
            MatrixMultiplier parallelMultiplier = new ParallelMultiplier();
            MatrixMultiplier sequentialMultiplier = new SequentialMultiplier();
            long startTime_parallel = System.currentTimeMillis();
            //int[] product_parllel = parallelMultiplier.multiplyMbyA(M, A);

            // SOMETHING IS WRONG WITH THE PARALLEL IMPLEMENTATION, DESPITE THAT IT IS AN EXACT
            // COPY OF THE PYTHON VERSION IN THIS FOLDER THAT WORKS. I'M GETTING OUTOFMEMORYERRORS
            // IN THE JAVA VERSION, THOUGH.

            int[] product_parllel = sequentialMultiplier.multiplyMbyA(M, A);
            long endTime_parallel = System.currentTimeMillis();
            long duration_parallel = endTime_parallel - startTime_parallel;
            System.out.println("parallel multiply "+N+"x"+N+" by "+N+"x1:   "+duration_parallel+" (millis)");

            if (N < 20) {
                printVector(product_parllel);
            }
        }
    }
}

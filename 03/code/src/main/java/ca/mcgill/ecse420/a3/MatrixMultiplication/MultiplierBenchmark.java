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
        final String cli_parallel_practical = "parallel_practical";
        Options cliOptions = new Options();
        cliOptions.addOption(Option
            .builder()
            .longOpt(cli_size)
            .hasArg()
            .argName("N")
            .type(int.class)
            .desc("Use NxN matrix and Nx1 vector for multiplication. N defaults to 2000.\n"
                 +"Note that if --parallel is given, N must be less than 200 to avoid out-of-memory errors.")
            .build()
        );
        cliOptions.addOption(Option
            .builder()
            .longOpt(cli_sequential)
            .desc("Do sequential matrix-vector multiplication")
            .build()
        );
        cliOptions.addOption(Option
            .builder()
            .longOpt(cli_parallel)
            .desc("Do parallel matrix-vector multiplication")
            .build()
        );
        cliOptions.addOption(Option
            .builder()
            .longOpt(cli_parallel_practical)
            .desc("Do parallel matrix-vector multiplication using more practical parallel algorithm")
            .build()
        );
        CommandLine parsedCli = new DefaultParser().parse(cliOptions, args);

        boolean doSequential = parsedCli.hasOption(cli_sequential);
        boolean doParallel = parsedCli.hasOption(cli_parallel);
        boolean doParallelPractical = parsedCli.hasOption(cli_parallel_practical);
        if (!(doSequential || doParallel || doParallelPractical)) {
            System.out.println("Must supply at least one of --sequential or --parallel or --parallel_practical");
            System.exit(1);
        }

        int N = parsedCli.hasOption(cli_size) ?
                Integer.parseInt(parsedCli.getOptionValue(cli_size)) :
                2000;

        // create input data
        int[][] M = makeRandomMatrix(N);
        int[] A = makeRandomVector(N);

        if (doSequential) {
            long startTime_sequential = System.currentTimeMillis();

            int[] product_sequential = new SequentialMultiplier().multiplyMbyA(M, A);

            long endTime_sequential = System.currentTimeMillis();
            long duration_sequential = endTime_sequential - startTime_sequential;
            System.out.println("sequential multiply "+N+"x"+N+" by "+N+"x1:           "+duration_sequential+" (millis)");
        }

        if (doParallel) {
            long startTime_parallel = System.currentTimeMillis();

            int[] product_parallel = new ParallelMultiplier().multiplyMbyA(M, A);

            long endTime_parallel = System.currentTimeMillis();
            long duration_parallel = endTime_parallel - startTime_parallel;
            System.out.println("parallel multiply "+N+"x"+N+" by "+N+"x1:             "+duration_parallel+" (millis)");
        }

        if (doParallelPractical) {
            long startTime_parallel_practical = System.currentTimeMillis();

            int[] product_parallel_practical = new ParallelMultiplier_Practical().multiplyMbyA(M, A);

            long endTime_parallel_practical = System.currentTimeMillis();
            long duration_parallel_practical = endTime_parallel_practical - startTime_parallel_practical;
            System.out.println("practical parallel multiply "+N+"x"+N+" by "+N+"x1:   "+duration_parallel_practical+" (millis)");
        }
    }
}

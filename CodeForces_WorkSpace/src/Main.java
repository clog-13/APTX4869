import java.util.*;

public class Main {
    int N, M;

    public static void main(String[] args) {
        new Main().init();
    }

    void init() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            N = sc.nextInt(); M = sc.nextInt();
            PII[] mchs = new PII[N], taks = new PII[M];
            for (int i = 0; i < N; i++) mchs[i] = new PII(sc.nextInt(), sc.nextInt());
            for (int i = 0; i < M; i++) taks[i] = new PII(sc.nextInt(), sc.nextInt());

            Arrays.sort(mchs);
            Arrays.sort(taks);


        }

    }
//        while (cin >> n >> m)
//        {
//            for (int i = 0; i < n; i ++ ) cin >> mchs[i].first >> mchs[i].second;
//            for (int i = 0; i < m; i ++ ) cin >> tasks[i].first >> tasks[i].second;
//            sort(mchs, mchs + n);
//            sort(tasks, tasks + m);
//            multiset<int> ys;
//            LL cnt = 0, res = 0;
//            for (int i = m - 1, j = n - 1; i >= 0; i -- )
//            {
//                while (j >= 0 && mchs[j].first >= tasks[i].first) ys.insert(mchs[j -- ].second);
//                auto it = ys.lower_bound(tasks[i].second);
//                if (it != ys.end())
//                {
//                    cnt ++ ;
//                    res += 500 * tasks[i].first + 2 * tasks[i].second;
//                    ys.erase(it);
//                }
//            }
//            cout << cnt << ' ' << res << endl;
//        }

    static class PII implements Comparable<PII> {
        int first, second;

        public PII(int f, int s) {
            first = f; second = s;
        }

        @Override
        public int compareTo(PII pii) {
            if (this.first == pii.first) return this.second - pii.second;
            else return this.first - pii.first;
        }
    }
}
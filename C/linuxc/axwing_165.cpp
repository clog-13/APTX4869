#include <iostream>
#include <cstdio>
#include <cstring>
using namespace std;
int N, arr[19], dp[20][1<<19], W;
int main() {
    memset(dp, 0x3f, sizeof dp);
    scanf("%d%d", &N, &W);
    for (int i = 1; i <= N; i++) {
        scanf("%d", &arr[i]);
        dp[1][1<<(i-1)] = arr[i];
    }
    dp[1][0] = 0;
    for (int i = 1; i <= N; i++) {  // 枚举车 
        for (int j = 0; j < (1<<N); j++) {  // 枚举状态
            if (dp[i][j] != 0x3f3f3f3f) {   // 如果这辆车被更新过 才能用来更新其他的
                for (int k = 1; k <= N; k++) {  // 枚举猫   
                    if (W-dp[i][j] >= arr[k] && (j&(1<<(k-1))) == 0)  // 如果能装下就装 
                        dp[i][j|(1<<(k-1))] = min(dp[i][j|(1<<(k-1))], dp[i][j]+arr[k]);  // 使已用体积最小 
                    else dp[i+1][j|(1<<(k-1))] = min(dp[i+1][j|(1<<(k-1))], arr[k]);  // 如果装不下就再开一辆车 
                } 
            }
        }
        
        if (dp[i][(1<<N)-1] != 0x3f3f3f3f) {    // 由于i从前往后循环，第一次所有猫都装下时，则为最少需要用车的数量 
            printf("%d\n", i);
            return 0;
        }
    }
    return 0;
}
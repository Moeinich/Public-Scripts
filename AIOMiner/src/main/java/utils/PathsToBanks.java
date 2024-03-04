package utils;

import helpers.utils.Tile;

public enum PathsToBanks {
    VARROCK_EAST_BANKPATHS(
            new Tile[] {
                    new Tile(2846, 988), new Tile(2850, 986), new Tile(2854, 982), new Tile(2854, 979), new Tile(2854, 972), new Tile(2853, 965), new Tile(2852, 961), new Tile(2853, 953), new Tile(2852, 949), new Tile(2850, 941), new Tile(2849, 934), new Tile(2847, 926), new Tile(2844, 922), new Tile(2837, 917), new Tile(2828, 914), new Tile(2819, 915), new Tile(2809, 912), new Tile(2802, 916)},
            new Tile[] {
                    new Tile(2849, 988), new Tile(2851, 986), new Tile(2854, 982), new Tile(2852, 974), new Tile(2852, 969), new Tile(2852, 964), new Tile(2851, 960), new Tile(2851, 955), new Tile(2850, 951), new Tile(2850, 944), new Tile(2849, 940), new Tile(2846, 935), new Tile(2841, 931), new Tile(2838, 926), new Tile(2835, 918), new Tile(2827, 914), new Tile(2818, 914), new Tile(2808, 912), new Tile(2802, 917)
            },
            new Tile[] {
                    new Tile(2844, 989), new Tile(2845, 988), new Tile(2849, 985), new Tile(2852, 982), new Tile(2854, 978), new Tile(2854, 972), new Tile(2854, 968), new Tile(2853, 961), new Tile(2852, 957), new Tile(2852, 951), new Tile(2851, 943), new Tile(2850, 937), new Tile(2848, 930), new Tile(2847, 924), new Tile(2844, 920), new Tile(2840, 918), new Tile(2833, 916), new Tile(2828, 914), new Tile(2820, 913), new Tile(2814, 913), new Tile(2806, 912), new Tile(2803, 916)
            }
    ),
    VARROCK_WEST_BANKPATHS(
            new Tile[] {
                    new Tile(2697, 981), new Tile(2696, 977), new Tile(2695, 970), new Tile(2693, 962), new Tile(2693, 953), new Tile(2692, 945), new Tile(2692, 938), new Tile(2692, 933), new Tile(2693, 927), new Tile(2693, 921), new Tile(2694, 918), new Tile(2697, 915), new Tile(2701, 911), new Tile(2708, 908)
            },
            new Tile[] {
                    new Tile(2700, 979), new Tile(2699, 973), new Tile(2698, 968), new Tile(2696, 960), new Tile(2695, 955), new Tile(2693, 947), new Tile(2693, 939), new Tile(2693, 932), new Tile(2693, 925), new Tile(2694, 921), new Tile(2699, 913), new Tile(2707, 909)
            },
            new Tile[] {
                    new Tile(2702, 978), new Tile(2700, 975), new Tile(2698, 969), new Tile(2696, 963), new Tile(2694, 959), new Tile(2693, 954), new Tile(2692, 948), new Tile(2692, 941), new Tile(2693, 935), new Tile(2693, 930), new Tile(2694, 925), new Tile(2694, 921), new Tile(2695, 916), new Tile(2701, 914), new Tile(2707, 908)
            }
    );

    // Enum setup
    private final Tile[] Path1;
    private final Tile[] Path2;
    private final Tile[] Path3;

    PathsToBanks(Tile[] Path1, Tile[] Path2, Tile[] Path3) {
        this.Path1 = Path1;
        this.Path2 = Path2;
        this.Path3 = Path3;
    }

    public Tile[] Path1() {
        return Path1;
    }
    public Tile[] Path2() {
        return Path2;
    }
    public Tile[] Path3() {
        return Path3;
    }
}

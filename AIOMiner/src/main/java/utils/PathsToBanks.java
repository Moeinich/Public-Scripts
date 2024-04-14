package utils;

import helpers.utils.Tile;

public enum PathsToBanks {
    VARROCK_EAST_BANKPATHS(
            new Tile[] {
                    new Tile(2848, 987), new Tile(2851, 984), new Tile(2855, 979), new Tile(2856, 970), new Tile(2852, 961), new Tile(2851, 951), new Tile(2851, 945), new Tile(2850, 938), new Tile(2847, 928), new Tile(2842, 920), new Tile(2834, 916), new Tile(2826, 913), new Tile(2814, 913), new Tile(2803, 912), new Tile(2802, 916), new Tile(2801, 923)
            },
            new Tile[] {
                    new Tile(2848, 987), new Tile(2850, 985), new Tile(2853, 981), new Tile(2855, 977), new Tile(2854, 970), new Tile(2853, 960), new Tile(2852, 954), new Tile(2851, 948), new Tile(2850, 943), new Tile(2849, 936), new Tile(2841, 931), new Tile(2838, 926), new Tile(2835, 922), new Tile(2833, 917), new Tile(2829, 912), new Tile(2821, 912), new Tile(2811, 913), new Tile(2803, 912), new Tile(2802, 918), new Tile(2800, 923)
            },
            new Tile[] {new Tile(2847, 987), new Tile(2851, 983), new Tile(2856, 979), new Tile(2855, 971), new Tile(2853, 963), new Tile(2851, 954), new Tile(2851, 947), new Tile(2851, 939), new Tile(2848, 932), new Tile(2846, 925), new Tile(2841, 920), new Tile(2834, 915), new Tile(2824, 913), new Tile(2815, 913), new Tile(2803, 912), new Tile(2801, 922)
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

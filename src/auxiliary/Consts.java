package auxiliary;

import main.Game;

public class Consts {
    public static final float GRAVITY = 0.04f * Game.SCALE;
    public static final int SPEED_ANI = 25;
    public static class ForWeapons{
        public static final int CORE_WEAPON_DEFAULT_WIDTH = 15;
        public static final int CORE_WEAPON_DEFAULT_HEIGHT = 15;
        public static final int CORE_WEAPON_WIDTH = (int)(Game.SCALE * CORE_WEAPON_DEFAULT_WIDTH);
        public static final int CORE_WEAPON_HEIGHT = (int)(Game.SCALE * CORE_WEAPON_DEFAULT_HEIGHT);
        public static final float SPEED = 0.75f * Game.SCALE;
    }
    public static class PrizeConstants {
        public static final int HEALTH_POTION = 0;
        public static final int POWER_POTION = 1;
        public static final int BOX1 = 2;
        public static final int BOX2 = 3;
        public static final int TRAP_1 = 4;
        public static final int WEAPON_LEFT = 5;
        public static final int WEAPON_RIGHT = 6;
        public static final int HEALTH_POTION_VALUE = 15;
        public static final int POWER_POTION_VALUE = 10;
        public static final int BOX_WIDTH_DEFAULT = 40;
        public static final int BOX_HEIGHT_DEFAULT = 30;
        public static final int BOX_WIDTH = (int) (Game.SCALE * BOX_WIDTH_DEFAULT);
        public static final int BOX_HEIGHT = (int) (Game.SCALE * BOX_HEIGHT_DEFAULT);
        public static final int POTION_WIDTH_DEFAULT = 12;
        public static final int POTION_HEIGHT_DEFAULT = 16;
        public static final int POTION_WIDTH = (int) (Game.SCALE * POTION_WIDTH_DEFAULT);
        public static final int POTION_HEIGHT = (int) (Game.SCALE * POTION_HEIGHT_DEFAULT);
        public static final int TRAP_1_WIDTH_DEFAULT = 32;
        public static final int TRAP_1_HEIGHT_DEFAULT = 32;
        public static final int TRAP_1_WIDTH = (int) (Game.SCALE * TRAP_1_WIDTH_DEFAULT);
        public static final int TRAP_1_HEIGHT = (int) (Game.SCALE * TRAP_1_HEIGHT_DEFAULT);
        public static final int WEAPON_WIDTH_DEFAULT = 40;
        public static final int WEAPON_HEIGHT_DEFAULT = 26;
        public static final int WEAPON_WIDTH = (int) (WEAPON_WIDTH_DEFAULT * Game.SCALE);
        public static final int WEAPON_HEIGHT = (int) (WEAPON_HEIGHT_DEFAULT * Game.SCALE);
        public static int GetSprite(int object_type) {
            switch (object_type) {
                case HEALTH_POTION, POWER_POTION:
                    return 7;
                case BOX1, BOX2:
                    return 7;
                case WEAPON_LEFT, WEAPON_RIGHT:
                    return 7;
            }
            return 1;
        }
    }
    public static class EnemyConstants {
        public static final int ENEMY = 0;
        public static final int STATE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;
        public static final int ENEMY_WIDTH_DEFAULT = 72;
        public static final int ENEMY_HEIGHT_DEFAULT = 32;
        public static final int ENEMY_WIDTH = (int) (ENEMY_WIDTH_DEFAULT * Game.SCALE);
        public static final int ENEMY_HEIGHT = (int) (ENEMY_HEIGHT_DEFAULT * Game.SCALE);
        public static final int ENEMY_OUTPUT_OFFSET_X = (int) (26 * Game.SCALE);
        public static final int ENEMY_OUTPUT_OFFSET_Y = (int) (13 * Game.SCALE);
        public static int GetActionsEnewy(int enemy_type, int enemy_state) {
            switch (enemy_type) {
                case ENEMY:
                    switch (enemy_state) {
                        case STATE:
                            return 10;
                        case RUNNING:
                            return 12;
                        case ATTACK:
                            return 11;
                        case HIT:
                            return 7;
                        case DEAD:
                            return 13;
                    }
            }
            return 0;
        }
        public static int GetMaxHealth(int enemy_type) {
            switch (enemy_type) {
                case ENEMY:
                    return 10;
                default:
                    return 1;
            }
        }
        public static int GetEnemyDealDamage(int enemy_type) {
            switch (enemy_type) {
                case ENEMY:
                    return 15;
                default:
                    return 0;
            }
        }
    }
    public static class UserInterface {
        public static class Buttons {
            public static final int DEFAULT_WIDTH_OF_BUTTON = 140;
            public static final int DEFAULT_HEIGHT_OF_BUTTON = 56;
            public static final int BUTTON_WIDTH = (int) (DEFAULT_WIDTH_OF_BUTTON * Game.SCALE);
            public static final int BUTTON_HEIGHT = (int) (DEFAULT_HEIGHT_OF_BUTTON * Game.SCALE);
        }
        public static class PRRButtons {
            public static final int DEFAULT_SIZE_PRR = 56;
            public static final int PRR_SIZE = (int) (DEFAULT_SIZE_PRR * Game.SCALE);
        }
    }
    public static class Direction{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }
    public static class ConstsPlayer{
        public static final int STATICS = 0;
        public static final int RUN = 1;
        public static final int JUMP = 2;
        public static final int FALL = 3;
        public static final int ATTACK_ONE = 4;
        public static final int HIT_ONCE = 5;
        public static final int DEAD = 6;

        public static int GetActions(int player_action){
            switch (player_action){
                case DEAD:
                    return 5;
                case RUN:
                    return 8;
                case STATICS:
                    return 5;
                case HIT_ONCE:
                    return 2;
                case JUMP:
                    return 8;
                case ATTACK_ONE:
                    return 6;
                case FALL:
                    return 1;
                default:
                    return 1;
            }
        }
    }
}


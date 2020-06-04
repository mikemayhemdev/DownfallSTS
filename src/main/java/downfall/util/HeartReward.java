package downfall.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rewards.RewardItem.RewardType;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile.SaveType;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import java.util.ArrayList;
import java.util.Iterator;

import downfall.downfallMod;
import downfall.events.HeartEvent;
import downfall.relics.HeartsMalice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HeartReward {
    private static final Logger logger = LogManager.getLogger(HeartReward.class.getName());
    private static final CharacterStrings characterStrings;
    public static final String[] NAMES;
    public static final String[] TEXT;
    public static final String[] UNIQUE_REWARDS;
    public String optionLabel = "";
    public HeartReward.NeowRewardType type;
    public HeartReward.NeowRewardDrawback drawback;
    private boolean activated;
    private int hp_bonus;
    private boolean cursed;
    private static final int GOLD_BONUS = 100;
    private static final int LARGE_GOLD_BONUS = 250;
    private HeartReward.NeowRewardDrawbackDef drawbackDef;

    private String fleeText = CardCrawlGame.languagePack.getCharacterString(downfallMod.makeID("Heart Event")).OPTIONS[5];

    public HeartReward(boolean firstMini) {
        this.drawback = HeartReward.NeowRewardDrawback.NONE;
        this.activated = false;
        this.hp_bonus = 0;
        this.cursed = false;
        this.hp_bonus = (int)((float)AbstractDungeon.player.maxHealth * 0.1F);
        HeartReward.NeowRewardDef reward;
        if (firstMini) {
            reward = new HeartReward.NeowRewardDef(HeartReward.NeowRewardType.THREE_ENEMY_KILL, fleeText);
        } else {
            reward = new HeartReward.NeowRewardDef(HeartReward.NeowRewardType.TEN_PERCENT_HP_BONUS, TEXT[7] + this.hp_bonus + " ]");
        }

        this.optionLabel = this.optionLabel + reward.desc;
        this.type = reward.type;
    }

    public HeartReward(int category) {
        this.drawback = HeartReward.NeowRewardDrawback.NONE;
        this.activated = false;
        this.hp_bonus = 0;
        this.cursed = false;
        this.hp_bonus = (int)((float)AbstractDungeon.player.maxHealth * 0.1F);
        ArrayList<HeartReward.NeowRewardDef> possibleRewards = this.getRewardOptions(category);
        HeartReward.NeowRewardDef reward = (HeartReward.NeowRewardDef)possibleRewards.get(HeartEvent.rng.random(0, possibleRewards.size() - 1));
        if (this.drawback != HeartReward.NeowRewardDrawback.NONE && this.drawbackDef != null) {
            this.optionLabel = this.optionLabel + this.drawbackDef.desc;
        }

        this.optionLabel = this.optionLabel + reward.desc;
        this.type = reward.type;
    }

    private ArrayList<HeartReward.NeowRewardDrawbackDef> getRewardDrawbackOptions() {
        ArrayList<HeartReward.NeowRewardDrawbackDef> drawbackOptions = new ArrayList();
        drawbackOptions.add(new HeartReward.NeowRewardDrawbackDef(HeartReward.NeowRewardDrawback.TEN_PERCENT_HP_LOSS, TEXT[17] + this.hp_bonus + TEXT[18]));
        drawbackOptions.add(new HeartReward.NeowRewardDrawbackDef(HeartReward.NeowRewardDrawback.NO_GOLD, TEXT[19]));
        drawbackOptions.add(new HeartReward.NeowRewardDrawbackDef(HeartReward.NeowRewardDrawback.CURSE, TEXT[20]));
        drawbackOptions.add(new HeartReward.NeowRewardDrawbackDef(HeartReward.NeowRewardDrawback.PERCENT_DAMAGE, TEXT[21] + AbstractDungeon.player.currentHealth / 10 * 3 + TEXT[29] + " "));
        return drawbackOptions;
    }

    private ArrayList<HeartReward.NeowRewardDef> getRewardOptions(int category) {
        ArrayList<HeartReward.NeowRewardDef> rewardOptions = new ArrayList();
        switch(category) {
            case 0:
                rewardOptions.add(new HeartReward.NeowRewardDef(HeartReward.NeowRewardType.THREE_CARDS, TEXT[0]));
                rewardOptions.add(new HeartReward.NeowRewardDef(HeartReward.NeowRewardType.ONE_RANDOM_RARE_CARD, TEXT[1]));
                rewardOptions.add(new HeartReward.NeowRewardDef(HeartReward.NeowRewardType.REMOVE_CARD, TEXT[2]));
                rewardOptions.add(new HeartReward.NeowRewardDef(HeartReward.NeowRewardType.UPGRADE_CARD, TEXT[3]));
                rewardOptions.add(new HeartReward.NeowRewardDef(HeartReward.NeowRewardType.TRANSFORM_CARD, TEXT[4]));
                rewardOptions.add(new HeartReward.NeowRewardDef(HeartReward.NeowRewardType.RANDOM_COLORLESS, TEXT[30]));
                break;
            case 1:
                rewardOptions.add(new HeartReward.NeowRewardDef(HeartReward.NeowRewardType.THREE_SMALL_POTIONS, TEXT[5]));
                rewardOptions.add(new HeartReward.NeowRewardDef(HeartReward.NeowRewardType.RANDOM_COMMON_RELIC, TEXT[6]));
                rewardOptions.add(new HeartReward.NeowRewardDef(HeartReward.NeowRewardType.TEN_PERCENT_HP_BONUS, TEXT[7] + this.hp_bonus + " ]"));

                rewardOptions.add(new HeartReward.NeowRewardDef(HeartReward.NeowRewardType.THREE_ENEMY_KILL, fleeText));
                rewardOptions.add(new HeartReward.NeowRewardDef(HeartReward.NeowRewardType.HUNDRED_GOLD, TEXT[8] + 100 + TEXT[9]));
                break;
            case 2:
                ArrayList<HeartReward.NeowRewardDrawbackDef> drawbackOptions = this.getRewardDrawbackOptions();
                this.drawbackDef = (HeartReward.NeowRewardDrawbackDef)drawbackOptions.get(HeartEvent.rng.random(0, drawbackOptions.size() - 1));
                this.drawback = this.drawbackDef.type;
                rewardOptions.add(new HeartReward.NeowRewardDef(HeartReward.NeowRewardType.RANDOM_COLORLESS_2, TEXT[31]));
                if (this.drawback != HeartReward.NeowRewardDrawback.CURSE) {
                    rewardOptions.add(new HeartReward.NeowRewardDef(HeartReward.NeowRewardType.REMOVE_TWO, TEXT[10]));
                }

                rewardOptions.add(new HeartReward.NeowRewardDef(HeartReward.NeowRewardType.ONE_RARE_RELIC, TEXT[11]));
                rewardOptions.add(new HeartReward.NeowRewardDef(HeartReward.NeowRewardType.THREE_RARE_CARDS, TEXT[12]));
                if (this.drawback != HeartReward.NeowRewardDrawback.NO_GOLD) {
                    rewardOptions.add(new HeartReward.NeowRewardDef(HeartReward.NeowRewardType.TWO_FIFTY_GOLD, TEXT[13] + 250 + TEXT[14]));
                }

                rewardOptions.add(new HeartReward.NeowRewardDef(HeartReward.NeowRewardType.TRANSFORM_TWO_CARDS, TEXT[15]));
                if (this.drawback != HeartReward.NeowRewardDrawback.TEN_PERCENT_HP_LOSS) {
                    rewardOptions.add(new HeartReward.NeowRewardDef(HeartReward.NeowRewardType.TWENTY_PERCENT_HP_BONUS, TEXT[16] + this.hp_bonus * 2 + " ]"));
                }
                break;
            case 3:
                rewardOptions.add(new HeartReward.NeowRewardDef(HeartReward.NeowRewardType.BOSS_RELIC, UNIQUE_REWARDS[0]));
        }

        return rewardOptions;
    }

    public void update() {
        if (this.activated) {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                switch(this.type) {
                    case UPGRADE_CARD:
                        AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                        c.upgrade();
                        AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
                        AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                        break;
                    case REMOVE_CARD:
                        CardCrawlGame.sound.play("CARD_EXHAUST");
                        AbstractDungeon.topLevelEffects.add(new PurgeCardEffect((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0), (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
                        AbstractDungeon.player.masterDeck.removeCard((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0));
                        break;
                    case REMOVE_TWO:
                        CardCrawlGame.sound.play("CARD_EXHAUST");
                        AbstractCard c2 = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                        AbstractCard c3 = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(1);
                        AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c2, (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F - 30.0F * Settings.scale, (float)(Settings.HEIGHT / 2)));
                        AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c3, (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F + 30.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F));
                        AbstractDungeon.player.masterDeck.removeCard(c2);
                        AbstractDungeon.player.masterDeck.removeCard(c3);
                        break;
                    case TRANSFORM_CARD:
                        AbstractDungeon.transformCard((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0), false, HeartEvent.rng);
                        AbstractDungeon.player.masterDeck.removeCard((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0));
                        AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(AbstractDungeon.getTransformedCard(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                        break;
                    case TRANSFORM_TWO_CARDS:
                        AbstractCard t1 = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                        AbstractCard t2 = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(1);
                        AbstractDungeon.player.masterDeck.removeCard(t1);
                        AbstractDungeon.player.masterDeck.removeCard(t2);
                        AbstractDungeon.transformCard(t1, false, HeartEvent.rng);
                        AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(AbstractDungeon.getTransformedCard(), (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F - 30.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F));
                        AbstractDungeon.transformCard(t2, false, HeartEvent.rng);
                        AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(AbstractDungeon.getTransformedCard(), (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F + 30.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F));
                        break;
                    default:
                        logger.info("[ERROR] Missing Neow Reward Type: " + this.type.name());
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.overlayMenu.cancelButton.hide();
                SaveHelper.saveIfAppropriate(SaveType.POST_NEOW);
                this.activated = false;
            }

            if (this.cursed) {
                this.cursed = !this.cursed;
                AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(AbstractDungeon.getCardWithoutRng(CardRarity.CURSE), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            }
        }

    }

    public void activate() {
        this.activated = true;
        switch(this.drawback) {
            case CURSE:
                this.cursed = true;
                break;
            case NO_GOLD:
                AbstractDungeon.player.loseGold(AbstractDungeon.player.gold);
                break;
            case TEN_PERCENT_HP_LOSS:
                AbstractDungeon.player.decreaseMaxHealth(this.hp_bonus);
                break;
            case PERCENT_DAMAGE:
                AbstractDungeon.player.damage(new DamageInfo((AbstractCreature)null, AbstractDungeon.player.currentHealth / 10 * 3, DamageType.HP_LOSS));
                break;
            default:
                logger.info("[ERROR] Missing Neow Reward Drawback: " + this.drawback.name());
        }

        switch(this.type) {
            case UPGRADE_CARD:
                AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 1, TEXT[27], true, false, false, false);
                break;
            case REMOVE_CARD:
                AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 1, TEXT[23], false, false, false, true);
                break;
            case REMOVE_TWO:
                AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 2, TEXT[24], false, false, false, false);
                break;
            case TRANSFORM_CARD:
                AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 1, TEXT[25], false, true, false, false);
                break;
            case TRANSFORM_TWO_CARDS:
                AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 2, TEXT[26], false, false, false, false);
                break;
            case RANDOM_COLORLESS_2:
                AbstractDungeon.cardRewardScreen.open(this.getColorlessRewardCards(true), (RewardItem)null, CardCrawlGame.languagePack.getUIString("CardRewardScreen").TEXT[1]);
                break;
            case RANDOM_COLORLESS:
                AbstractDungeon.cardRewardScreen.open(this.getColorlessRewardCards(false), (RewardItem)null, CardCrawlGame.languagePack.getUIString("CardRewardScreen").TEXT[1]);
                break;
            case THREE_RARE_CARDS:
                AbstractDungeon.cardRewardScreen.open(this.getRewardCards(true), (RewardItem)null, TEXT[22]);
                break;
            case HUNDRED_GOLD:
                CardCrawlGame.sound.play("GOLD_JINGLE");
                AbstractDungeon.player.gainGold(100);
                break;
            case ONE_RANDOM_RARE_CARD:
                AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(AbstractDungeon.getCard(CardRarity.RARE, HeartEvent.rng).makeCopy(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                break;
            case RANDOM_COMMON_RELIC:
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), AbstractDungeon.returnRandomRelic(RelicTier.COMMON));
                break;
            case ONE_RARE_RELIC:
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), AbstractDungeon.returnRandomRelic(RelicTier.RARE));
                break;
            case BOSS_RELIC:
                AbstractDungeon.player.loseRelic(((AbstractRelic)AbstractDungeon.player.relics.get(0)).relicId);
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), AbstractDungeon.returnRandomRelic(RelicTier.BOSS));
                break;
            case THREE_ENEMY_KILL:
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new HeartsMalice());
                break;
            case TEN_PERCENT_HP_BONUS:
                AbstractDungeon.player.increaseMaxHp(this.hp_bonus, true);
                break;
            case THREE_CARDS:
                AbstractDungeon.cardRewardScreen.open(this.getRewardCards(false), (RewardItem)null, CardCrawlGame.languagePack.getUIString("CardRewardScreen").TEXT[1]);
                break;
            case THREE_SMALL_POTIONS:
                CardCrawlGame.sound.play("POTION_1");

                int remove;
                for(remove = 0; remove < 3; ++remove) {
                    AbstractDungeon.getCurrRoom().addPotionToRewards(PotionHelper.getRandomPotion());
                }

                AbstractDungeon.combatRewardScreen.open();
                AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.0F;
                remove = -1;

                for(int j = 0; j < AbstractDungeon.combatRewardScreen.rewards.size(); ++j) {
                    if (((RewardItem)AbstractDungeon.combatRewardScreen.rewards.get(j)).type == RewardType.CARD) {
                        remove = j;
                        break;
                    }
                }

                if (remove != -1) {
                    AbstractDungeon.combatRewardScreen.rewards.remove(remove);
                }
                break;
            case TWENTY_PERCENT_HP_BONUS:
                AbstractDungeon.player.increaseMaxHp(this.hp_bonus * 2, true);
                break;
            case TWO_FIFTY_GOLD:
                CardCrawlGame.sound.play("GOLD_JINGLE");
                AbstractDungeon.player.gainGold(250);
        }

        CardCrawlGame.metricData.addNeowData(this.type.name(), this.drawback.name());
    }

    public ArrayList<AbstractCard> getColorlessRewardCards(boolean rareOnly) {
        ArrayList<AbstractCard> retVal = new ArrayList();
        int numCards = 3;

        for(int i = 0; i < numCards; ++i) {
            CardRarity rarity = this.rollRarity();
            if (rareOnly) {
                rarity = CardRarity.RARE;
            } else if (rarity == CardRarity.COMMON) {
                rarity = CardRarity.UNCOMMON;
            }

            AbstractCard card;
            for(card = AbstractDungeon.getColorlessCardFromPool(rarity); retVal.contains(card); card = AbstractDungeon.getColorlessCardFromPool(rarity)) {
            }

            retVal.add(card);
        }

        ArrayList<AbstractCard> retVal2 = new ArrayList();
        Iterator var8 = retVal.iterator();

        while(var8.hasNext()) {
            AbstractCard c = (AbstractCard)var8.next();
            retVal2.add(c.makeCopy());
        }

        return retVal2;
    }

    public ArrayList<AbstractCard> getRewardCards(boolean rareOnly) {
        ArrayList<AbstractCard> retVal = new ArrayList();
        int numCards = 3;

        for(int i = 0; i < numCards; ++i) {
            CardRarity rarity = this.rollRarity();
            if (rareOnly) {
                rarity = CardRarity.RARE;
            }

            AbstractCard card = null;
            switch(rarity) {
                case RARE:
                    card = this.getCard(rarity);
                    break;
                case UNCOMMON:
                    card = this.getCard(rarity);
                    break;
                case COMMON:
                    card = this.getCard(rarity);
                    break;
                default:
                    logger.info("WTF?");
            }

            while(retVal.contains(card)) {
                card = this.getCard(rarity);
            }

            retVal.add(card);
        }

        ArrayList<AbstractCard> retVal2 = new ArrayList();
        Iterator var8 = retVal.iterator();

        while(var8.hasNext()) {
            AbstractCard c = (AbstractCard)var8.next();
            retVal2.add(c.makeCopy());
        }

        return retVal2;
    }

    public CardRarity rollRarity() {
        return HeartEvent.rng.randomBoolean(0.33F) ? CardRarity.UNCOMMON : CardRarity.COMMON;
    }

    public AbstractCard getCard(CardRarity rarity) {
        switch(rarity) {
            case RARE:
                return AbstractDungeon.rareCardPool.getRandomCard(HeartEvent.rng);
            case UNCOMMON:
                return AbstractDungeon.uncommonCardPool.getRandomCard(HeartEvent.rng);
            case COMMON:
                return AbstractDungeon.commonCardPool.getRandomCard(HeartEvent.rng);
            default:
                logger.info("Error in getCard in Neow Reward");
                return null;
        }
    }

    static {
        characterStrings = CardCrawlGame.languagePack.getCharacterString("Neow Reward");
        NAMES = characterStrings.NAMES;
        TEXT = characterStrings.TEXT;
        UNIQUE_REWARDS = characterStrings.UNIQUE_REWARDS;
    }

    public static enum NeowRewardDrawback {
        NONE,
        TEN_PERCENT_HP_LOSS,
        NO_GOLD,
        CURSE,
        PERCENT_DAMAGE;

        private NeowRewardDrawback() {
        }
    }

    public static enum NeowRewardType {
        RANDOM_COLORLESS_2,
        THREE_CARDS,
        ONE_RANDOM_RARE_CARD,
        REMOVE_CARD,
        UPGRADE_CARD,
        RANDOM_COLORLESS,
        TRANSFORM_CARD,
        THREE_SMALL_POTIONS,
        RANDOM_COMMON_RELIC,
        TEN_PERCENT_HP_BONUS,
        HUNDRED_GOLD,
        THREE_ENEMY_KILL,
        REMOVE_TWO,
        TRANSFORM_TWO_CARDS,
        ONE_RARE_RELIC,
        THREE_RARE_CARDS,
        TWO_FIFTY_GOLD,
        TWENTY_PERCENT_HP_BONUS,
        BOSS_RELIC;

        private NeowRewardType() {
        }
    }

    public static class NeowRewardDrawbackDef {
        public HeartReward.NeowRewardDrawback type;
        public String desc;

        public NeowRewardDrawbackDef(HeartReward.NeowRewardDrawback type, String desc) {
            this.type = type;
            this.desc = desc;
        }
    }

    public static class NeowRewardDef {
        public HeartReward.NeowRewardType type;
        public String desc;

        public NeowRewardDef(HeartReward.NeowRewardType type, String desc) {
            this.type = type;
            this.desc = desc;
        }
    }
}

package awakenedOne.potions;


import automaton.AutomatonMod;
import automaton.powers.CleanCodePower;
import automaton.powers.FreeFunctionPower;
import awakenedOne.AwakenedOneMod;
import awakenedOne.powers.ThaumaturgyPower;
import basemod.abstracts.CustomPotion;
import champ.actions.DiscoverOpenerAction;
import champ.potions.OpenerPotion;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.powers.CuriosityPower;
import com.megacrit.cardcrawl.vfx.combat.LightBulbEffect;


public class SacramentalWine extends CustomPotion {
    public static final String POTION_ID = "awakened:SacramentalWine";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public SacramentalWine() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.M, PotionColor.ATTACK);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = AwakenedOneMod.potionLabColor;
    }

    public void initializeData() {
        this.potency = getPotency();
        this.description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(new LightBulbEffect(AbstractDungeon.player.hb)));
        } else {
            this.addToBot(new VFXAction(new LightBulbEffect(AbstractDungeon.player.hb), 0.2F));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ThaumaturgyPower(potency)));
        AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.POWER).makeCopy();
        c.setCostForTurn(0);
        this.addToBot(new MakeTempCardInHandAction(c, true));
    }

    public CustomPotion makeCopy() {
        return new SacramentalWine();
    }

    public int getPotency(int ascensionLevel) {
        return 3;
    }
}

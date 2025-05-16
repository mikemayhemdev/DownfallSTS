package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.cards.tokens.Ceremony;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import collector.actions.DrawCardFromCollectionAction;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.CurseOfTheBell;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import static awakenedOne.AwakenedOneMod.makeRelicOutlinePath;
import static awakenedOne.AwakenedOneMod.makeRelicPath;
import static collector.util.Wiz.atb;
import static hermit.util.Wiz.getLowestHealthEnemy;

public class DeadBird extends CustomRelic implements OnReceivePowerRelic {

    public static final String ID = AwakenedOneMod.makeID("DeadBird");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("DeadBird.png")); //TODO: Images
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("DeadBird.png"));

    public DeadBird() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    //Corvid Spirit

    //or, Eve's Dead Bird

    private static final int AMOUNT = 4;

    @Override
    public boolean onReceivePower(AbstractPower var1, AbstractCreature var2) {

        int temp = AMOUNT;

        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
            temp += AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
        }

        if (temp > 0) {
            this.counter = AMOUNT + temp;
        }

        return true;
    }


    @Override
    public void onEquip() {
        this.counter = AMOUNT;
    }

    @Override
    public void atBattleStart() {
        this.counter = AMOUNT;
    }




    //Can't believe this was already pre-coded in Hermit's Wiz. Very helpful.
    @Override
    public void onPlayerEndTurn() {
        flash();

        int temp = AMOUNT;

        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
            temp += AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
        }

        this.addToTop(new RelicAboveCreatureAction(getLowestHealthEnemy(), this));

        AbstractDungeon.actionManager.addToBottom(new DamageAction(getLowestHealthEnemy(), new DamageInfo(AbstractDungeon.player, temp, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

}

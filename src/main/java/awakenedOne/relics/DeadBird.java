package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.AwakenedOneMod.makeRelicOutlinePath;
import static awakenedOne.AwakenedOneMod.makeRelicPath;
import static hermit.util.Wiz.getLowestHealthEnemy;

public class DeadBird extends CustomRelic {

    public static final String ID = AwakenedOneMod.makeID("DeadBird");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("DeadBird.png")); //TODO: Images
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("DeadBird.png"));

    public DeadBird() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    //Corvid Spirit

    //or, Eve's Dead Bird

    //damage
    private static final int AMOUNT = 4;

    public void update() {
        super.update();
        if (CardCrawlGame.isInARun()) {
            if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
                this.counter = AMOUNT + AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
            }

            if (this.counter < AMOUNT) {
                this.counter = AMOUNT;
            }
        }
    }


    @Override
    public void onEquip() {
        this.counter = AMOUNT;
    }

    @Override
    public void atBattleStart() {
        this.counter = AMOUNT;
    }

    @Override
    public void onVictory(){
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

        if (temp < AMOUNT) {
            temp = AMOUNT;
        }


        int nocrashpls = 0;

        for (final AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDying && m.currentHealth > 0) {
                nocrashpls++;
            }
        }


        if (nocrashpls > 0) {
            this.addToTop(new RelicAboveCreatureAction(getLowestHealthEnemy(), this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(getLowestHealthEnemy(), new DamageInfo(AbstractDungeon.player, temp, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

}

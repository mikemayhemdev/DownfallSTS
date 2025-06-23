package awakenedOne.cards;

import awakenedOne.powers.SheerTerrorPower;
import awakenedOne.relics.KTRibbon;
import awakenedOne.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.ui.AwakenButton.awaken;

public class SheerTerror extends AbstractAwakenedCard {
    public final static String ID = makeID(SheerTerror.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public SheerTerror() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        //this.exhaust = true;
        loadJokeCardImage(this, makeBetaCardPath(ID + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_AWAKENEDONE_3"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new ShockWaveEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, new Color(0.1F, 0.0F, 0.2F, 1.0F), ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new ShockWaveEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, new Color(0.3F, 0.2F, 0.4F, 1.0F), ShockWaveEffect.ShockWaveType.CHAOTIC), 1.0F));

            HexCurse(magicNumber, m, p);


        if (Wiz.isChantActive()) {
            this.addToBot(new ApplyPowerAction(m, p, new SheerTerrorPower(m, 1), 1, true, AbstractGameAction.AttackEffect.POISON));
            chant();
        }

        if ((!Wiz.isChantActive()) && AbstractDungeon.player.hasRelic(KTRibbon.ID)) {
            if ((AbstractDungeon.player.getRelic(KTRibbon.ID).counter == -1)) {
                this.addToBot(new ApplyPowerAction(m, p, new SheerTerrorPower(m, 1), 1, true, AbstractGameAction.AttackEffect.POISON));
                chant();
                awaken(1);
            }
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = isChantActiveGlow(this) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public void chant() {
        checkOnChant();
    }


    public void upp() {
        //upgradeMagicNumber(1);
        upgradeBaseCost(0);
    }
}
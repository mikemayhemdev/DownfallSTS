package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.NihilAction;
import awakenedOne.powers.ManaburnPower;
import awakenedOne.relics.KTRibbon;
import champ.actions.VigorWallopAction;
import collector.powers.AddCopyNextTurnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

import static awakenedOne.AwakenedOneMod.HexCurse;
import static awakenedOne.ui.AwakenButton.awaken;
import static awakenedOne.util.Wiz.atb;
import static collector.util.Wiz.applyToSelfTop;


public class ExNihilo extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(ExNihilo.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public ExNihilo() {
        super(ID, 0, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 0;
        this.exhaust = true;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {

        if (chant) {
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new NihilAction((AbstractCreature)m, (AbstractCreature)p, this.damage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
            checkOnChant();
        }

        if ((!chant) && AbstractDungeon.player.hasRelic(KTRibbon.ID)) {
            if ((AbstractDungeon.player.getRelic(KTRibbon.ID).counter == -1)) {
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new NihilAction((AbstractCreature)m, (AbstractCreature)p, this.damage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
                checkOnChant();
                awaken(1);
            }
        }

    }

    @Override
    public void chant() {
        checkOnChant();
    }

    public void triggerOnGlowCheck() {
        this.glowColor = chant ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public void upp() {
        this.exhaust = false;
    }
}
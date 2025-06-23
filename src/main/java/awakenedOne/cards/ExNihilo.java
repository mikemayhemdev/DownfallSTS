package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.NihilAction;
import awakenedOne.relics.KTRibbon;
import awakenedOne.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.ui.AwakenButton.awaken;


public class ExNihilo extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(ExNihilo.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public ExNihilo() {
        super(ID, 0, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 3;
        this.exhaust = true;
        loadJokeCardImage(this, ID+".png");
    }


    public void use(AbstractPlayer p, AbstractMonster m) {

        if (Wiz.isChantActive()) {
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new NihilAction((AbstractCreature)m, (AbstractCreature)p, this.damage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
            checkOnChant();
        }

        if ((!Wiz.isChantActive()) && AbstractDungeon.player.hasRelic(KTRibbon.ID)) {
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
        this.glowColor = isChantActiveGlow(this) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public void upp() {
        this.exhaust = false;
    }
}
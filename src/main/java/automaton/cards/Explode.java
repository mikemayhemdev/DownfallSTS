package automaton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;

public class Explode extends AbstractBronzeCard {
    public final static String ID = makeID("Explode");

    public Explode() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        baseMagicNumber = magicNumber = 15;
        thisEncodes();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        shuffleIn(new Burn());
    }


    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        if (forGameplay) {
            addToBot(new VFXAction(new ExplosionSmallEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
            for (AbstractMonster q : monsterList()) {
                if (!q.isDeadOrEscaped())
                addToBot(new LoseHPAction(q, q, magicNumber, AbstractGameAction.AttackEffect.FIRE));
            }
        }
    }

    @Override
    public void upp() {
        upgradeMagicNumber(5);
    }
}

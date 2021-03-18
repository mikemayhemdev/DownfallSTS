package automaton.cards;

import automaton.actions.AddToFuncAction;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;

public class RecursiveStrike extends AbstractBronzeCard {

    public final static String ID = makeID("RecursiveStrike");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 6;

    public RecursiveStrike() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = 2;
        thisEncodes();
        cardsToPreview = new Strike();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            if (m != null)
                this.addToBot(new VFXAction(new ClawEffect(m.hb.cX, m.hb.cY, Color.ORANGE, Color.WHITE), 0.1F));
            dmg(m, AbstractGameAction.AttackEffect.NONE);
        }
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        if (forGameplay) {
            for (int i = 0; i < magicNumber; i++) {
                AbstractCard c = new Strike();
                if (upgraded) c.upgrade();
                atb(new AddToFuncAction(c, null));
            }
        }
    }

    public void upp() {
        cardsToPreview.upgrade();
        upgradeDamage(3);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}
package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;

public class ShadowStrike extends AbstractHexaCard {

    public final static String ID = makeID("ShadowStrike");

    //stupid intellij stuff ATTACK, ENEMY, SPECIAL

    private static final int DAMAGE = 16;
    private static final int UPG_DAMAGE = 4;

    private AbstractCard parent;

    public ShadowStrike(AbstractCard parent) {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CardColor.COLORLESS);
        baseDamage = DAMAGE;
        exhaust = true;
        isEthereal = true;
        tags.add(CardTags.STRIKE);
        this.parent = parent;
        cardsToPreview = new NightmareStrike();
    }

    public ShadowStrike() {
        this(null);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (parent != null && p.exhaustPile.contains(parent)) {
                    att(new AbstractGameAction() {
                        @Override
                        public void update() {
                            isDone = true;
                            p.exhaustPile.removeCard(parent);
                            AbstractDungeon.effectsQueue.add(new ShowCardAndAddToDiscardEffect(parent.makeSameInstanceOf()));
                        }
                    });
                }
            }
        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}
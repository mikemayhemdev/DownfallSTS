package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;

public class ShadowGuise extends AbstractHexaCard {

    public final static String ID = makeID("ShadowGuise");

    //stupid intellij stuff SKILL, SELF, SPECIAL

    private static final int BLOCK = 14;
    private static final int UPG_BLOCK = 4;

    private AbstractCard parent;

    public ShadowGuise(AbstractCard parent) {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        baseBlock = BLOCK;
        exhaust = true;
        this.parent = parent;
        cardsToPreview = new NightmareGuise();
    }

    public ShadowGuise() {
        this(null);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
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
            upgradeBlock(UPG_BLOCK);
        }
    }
}
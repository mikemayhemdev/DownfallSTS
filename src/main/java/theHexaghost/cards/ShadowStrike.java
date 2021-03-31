package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import slimebound.SlimeboundMod;

public class ShadowStrike extends AbstractHexaCard {

    public final static String ID = makeID("ShadowStrike");

    //stupid intellij stuff ATTACK, ENEMY, SPECIAL

    private static final int DAMAGE = 16;
    private static final int UPG_DAMAGE = 4;

    private AbstractCard parent;

    public ShadowStrike(AbstractCard parent) {
        super(ID, 2, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CardColor.COLORLESS);
        baseDamage = DAMAGE;
        exhaust = true;
        isEthereal = true;
        tags.add(CardTags.STRIKE);
        this.parent = parent;
        if (parent != null)
            cardsToPreview = this.parent.makeStatEquivalentCopy();
    }

    public ShadowStrike() {
        this(null);
    }

    public void setParent(AbstractCard parent) {
        this.parent = parent;
        cardsToPreview = this.parent.makeStatEquivalentCopy();
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

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        ShadowStrike card = (ShadowStrike) this.makeCopy();

        for(int i = 0; i < this.timesUpgraded; ++i) {
            card.upgrade();
        }

        card.name = this.name;
        card.target = this.target;
        card.upgraded = this.upgraded;
        card.timesUpgraded = this.timesUpgraded;
        card.baseDamage = this.baseDamage;
        card.baseBlock = this.baseBlock;
        card.baseMagicNumber = this.baseMagicNumber;
        card.cost = this.cost;
        card.costForTurn = this.costForTurn;
        card.isCostModified = this.isCostModified;
        card.isCostModifiedForTurn = this.isCostModifiedForTurn;
        card.inBottleLightning = this.inBottleLightning;
        card.inBottleFlame = this.inBottleFlame;
        card.inBottleTornado = this.inBottleTornado;
        card.isSeen = this.isSeen;
        card.isLocked = this.isLocked;
        card.misc = this.misc;
        card.freeToPlayOnce = this.freeToPlayOnce;
        card.setParent(this.parent);
        return card;
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}
package collector.cards.Collectibles;

import collector.CollectorMod;
import collector.powers.PlayerCurlUp;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LouseSegment extends AbstractCollectibleCard {
    public final static String ID = makeID("LouseSegment");

    public LouseSegment() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseDamage = 1;
        block = baseBlock = 8;
        baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new PlayerCurlUp(p,block));
        AbstractCard card = this;
        atb(new AbstractGameAction() {
            public void update() {
                CollectorMod.PerpetualEffect(card);
                this.isDone = true;
            }});
    }

    @Override
    public void upp() {
        upgradeBlock(4);
    }
}
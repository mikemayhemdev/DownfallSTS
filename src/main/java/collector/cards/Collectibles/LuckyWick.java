package collector.cards.Collectibles;

import collector.CollectorMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LuckyWick extends AbstractCollectibleCard {
    public final static String ID = makeID("LuckyWick");
    public int perpetualbonus;
    public LuckyWick() {
        super(ID, 0, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        damage = baseDamage = 1;
        block = baseBlock = 1;
        magicNumber = baseMagicNumber = 1;
        perpetualbonus = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        blck();
        atb(new DrawCardAction(p,magicNumber));
        AbstractCard card = this;
        atb(new AbstractGameAction() {
            public void update() {
                CollectorMod.PerpetualEffect(card,perpetualbonus);
                this.isDone = true;
            }});
    }

    @Override
    public void upp() {
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        perpetualbonus = 1;
        initializeDescription();
    }
}

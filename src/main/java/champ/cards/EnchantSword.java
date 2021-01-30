package champ.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnchantSword extends AbstractChampCard {

    public final static String ID = makeID("EnchantSword");

    //stupid intellij stuff skill, self, uncommon

    public EnchantSword() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
        magicNumber = baseMagicNumber = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new SelectCardsInHandAction(1, CardCrawlGame.languagePack.getUIString("champ:EnchantUI").TEXT[1], c -> c.baseDamage > 0, (cards) -> {
            cards.get(0).baseDamage += magicNumber;

        }));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = false;
        for (AbstractCard c : p.hand.group) {
            if (c.baseDamage > 0) {
                canUse = true;
                break;
            }
        }
        if (!canUse) {
            cantUseMessage = EXTENDED_DESCRIPTION[0];
            return false;
        }
        return super.canUse(p, m);
    }

    public void upp() {
        //  tags.add(ChampMod.TECHNIQUE);
        upgradeBaseCost(0);
    }
}
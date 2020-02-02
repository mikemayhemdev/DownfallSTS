package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.TheSnecko;
import sneckomod.actions.MuddleAction;
import sneckomod.actions.RandomDamageAction;

public class WideSting extends AbstractSneckoCard {

    public final static String ID = makeID("WideSting");

    //stupid intellij stuff ATTACK, ALL, COMMON

    private static final int DAMAGE = 12;
    private static final int MAGIC = 7;

    public WideSting() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }

    @Override
    public void calculateCardDamage(final AbstractMonster mo) {
        int CURRENT_MAGIC_NUMBER = baseMagicNumber;
        int CURRENT_DMG = baseDamage;
        baseDamage = CURRENT_MAGIC_NUMBER;
        super.calculateCardDamage(mo); // takes baseDamage and applies things like Strength or Pen Nib to set damage

        magicNumber = damage; // magic number holds the first condition's modified damage, so !M! will work
        isMagicNumberModified = magicNumber != baseMagicNumber;

        // repeat so damage holds the second condition's damage
        baseDamage = CURRENT_DMG;
        super.calculateCardDamage(mo);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster q : monsterList()) {
            atb(new RandomDamageAction(q, magicNumber, damage, 1, AbstractGameAction.AttackEffect.LIGHTNING));
        }
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (AbstractCard q : p.hand.group) {
                    if (q.color != TheSnecko.Enums.SNECKO_CYAN) {
                        atb(new UpgradeSpecificCardAction(q));
                        atb(new MuddleAction(q));
                    }
                }
            }
        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}
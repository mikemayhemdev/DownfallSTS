package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;

public class MixItUp extends AbstractSneckoCard {

    public final static String ID = makeID("MixItUp");

    //stupid intellij stuff ATTACK, ENEMY, UNCOMMON

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 2;

    public MixItUp() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        exhaust = true;
        tags.add(CardTags.HEALING);
        // tags.add(SneckoMod.RNG);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractPotion q : p.potions) {
            if (!(q instanceof PotionSlot)) {
                dmg(m, makeInfo(), AbstractGameAction.AttackEffect.BLUNT_LIGHT);
            }
        }
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (AbstractPotion q : p.potions) {
                    if (!(q instanceof PotionSlot)) {
                        p.removePotion(q);
                        att(new ObtainPotionAction(PotionHelper.getRandomPotion()));
                    }
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
/*
package champ.cards;

import champ.powers.ResolvePower;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class FightOn extends AbstractChampCard {

    public final static String ID = makeID("FightOn");

    //stupid intellij stuff skill, self, uncommon

    public FightOn() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        FleetingField.fleeting.set(this, true);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        this.tags.add(CardTags.HEALING);
        baseMagicNumber = magicNumber = 30;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                p.increaseMaxHp(5, true);
            }
        });
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(ResolvePower.POWER_ID)) {
            if (p.getPower(ResolvePower.POWER_ID).amount >= magicNumber) {
                return super.canUse(p, m);
            }
        }
        cantUseMessage = "I have not Fatigued enough health...";
        return false;
    }

    @Override
    public void upgrade() {
        upgradeName();
        upgradeMagicNumber(-20);
    }

    public void upp() {
    }
}

 */
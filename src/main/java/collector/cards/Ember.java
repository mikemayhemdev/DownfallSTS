package collector.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import expansioncontent.expansionContentMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class Ember extends AbstractCollectorCard {
    public final static String ID = makeID(Ember.class.getSimpleName());
    // intellij stuff skill, none, special, , , , , 1, 1

    public Ember() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, CardColor.COLORLESS);
        baseMagicNumber = magicNumber = 1;
        baseBlock = 4;
        selfRetain = true;
        exhaust = true;
        tags.add(expansionContentMod.UNPLAYABLE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void triggerOnExhaust() {
        this.addToBot(new VFXAction(AbstractDungeon.player, new InflameEffect(AbstractDungeon.player), 0.1F));
        applyToSelf(new StrengthPower(AbstractDungeon.player, magicNumber));
        blck();
    }

    public void upp() {
        upgradeMagicNumber(1);
        upgradeBlock(2);
    }
}
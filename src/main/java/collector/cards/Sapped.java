package collector.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import expansioncontent.expansionContentMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class Sapped extends AbstractCollectorCard {
    public final static String ID = makeID(Sapped.class.getSimpleName());
    // intellij stuff skill, none, special, , , , , 1, 1

    public Sapped() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, CardColor.COLORLESS);
        baseMagicNumber = magicNumber = 1;
        isPyre();
        exhaust = true;
        tags.add(expansionContentMod.UNPLAYABLE);
        SoulboundField.soulbound.set(this, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}
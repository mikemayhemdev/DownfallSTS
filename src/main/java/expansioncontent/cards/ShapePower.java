package expansioncontent.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;
import expansioncontent.expansionContentMod;

import static expansioncontent.expansionContentMod.loadJokeCardImage;

import java.util.ArrayList;

public class ShapePower extends AbstractExpansionCard {
    public final static String ID = makeID("ShapePower");

    public ShapePower() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_donudeca.png", "expansioncontentResources/images/1024/bg_boss_donudeca.png");
        tags.add(expansionContentMod.STUDY_SHAPES);
        tags.add(expansionContentMod.STUDY);
        baseMagicNumber = magicNumber = 2;
        baseDownfallMagic = downfallMagic = 4;
        loadJokeCardImage(this, "ShapePower.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
        atb(new ApplyPowerAction(p, p, new PlatedArmorPower(p, downfallMagic)));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
        upgradeDownfall(1);
    }
}
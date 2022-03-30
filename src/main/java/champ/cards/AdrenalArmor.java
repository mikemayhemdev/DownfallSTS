package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static champ.ChampMod.fatigue;

public class AdrenalArmor extends AbstractChampCard {

    public final static String ID = makeID("AdrenalArmor");

    //stupid intellij stuff skill, self, common

    private static final int BLOCK = 7;
    private static final int MAGIC = 2;

    public AdrenalArmor() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        //tags.add(ChampMod.COMBO);
        //tags.add(ChampMod.COMBOBERSERKER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new StrengthPower(p, magicNumber));
        applyToSelf(new LoseStrengthPower(p, magicNumber));
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = bcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(2);
    }
}
package hermit.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import hermit.HermitMod;
import hermit.actions.CheatAction;
import hermit.characters.hermit;

import static hermit.HermitMod.loadJokeCardImage;
import static hermit.HermitMod.makeCardPath;

public class Cheat extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(Cheat.class.getSimpleName());
    public static final String IMG = makeCardPath("cheat.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = hermit.Enums.COLOR_YELLOW;

    private static final int COST = 1;
    private int trig_amount = 1;


    // /STAT DECLARATION/

    public Cheat() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 3;
        //this.cardsToPreview = new Shame();
        this.tags.add(Enums.DEADON);
        loadJokeCardImage(this, "cheat.png");
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        trig_amount = 1;

        if (isDeadOn()) {
            TriggerDeadOnEffect(p,m); // Of note is that Cheat's Dead On is now properly triggered "twice".
        }
        else                        // Cheated without any dead on stuff.
        this.addToBot(new CheatAction(this.magicNumber,this,isDeadOn(),trig_amount));
    }

    @Override
    public void DeadOnEffectStacking(AbstractPlayer p, AbstractMonster m, int val)
    {
        trig_amount = val;

        // Repeated to maintain dead on trigger order.
        // By definition, isdeadon is true here.
        this.addToBot(new CheatAction(this.magicNumber,this,true,trig_amount));
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractDynamicCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (isDeadOnPos()) {
            this.glowColor = AbstractDynamicCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }
}
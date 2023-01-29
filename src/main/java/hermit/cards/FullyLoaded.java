package hermit.cards;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.HermitMod;
import hermit.actions.SoundAction;
import hermit.characters.hermit;
import hermit.util.Wiz;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static hermit.HermitMod.*;

public class FullyLoaded extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(FullyLoaded.class.getSimpleName());
    public static final String IMG = makeCardPath("fully_loaded.png");

    // /TEXT DECLARATION/

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = hermit.Enums.COLOR_YELLOW;



    private static final int COST = 0;


    // /STAT DECLARATION/

    public FullyLoaded() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust=true;
        loadJokeCardImage(this, "fully_loaded.png");
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.playAV(makeID("SPIN"), 1.0f, 1.25f); // Sound Effect
        Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                tmp.group = (ArrayList<AbstractCard>) Wiz.p().drawPile.group.stream()
                        .filter(c -> c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || c.hasTag(AbstractCard.CardTags.STARTER_DEFEND))
                        .collect(Collectors.toList());
                for (AbstractCard c : tmp.group) {
                    if (!tmp.isEmpty() && Wiz.hand().size() < BaseMod.MAX_HAND_SIZE) {
                        Wiz.att(new FetchAction(Wiz.p().drawPile,card -> card == c));
                    }
                }
                isDone = true;
            }
        });
        this.addToBot(new SoundAction(makeID("RELOAD")));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();

            this.selfRetain = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
package timeeater.cards;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.actions.EasyModalChoiceAction;

import static timeeater.TimeEaterMod.makeID;

@AutoAdd.Ignore
public class EasyModalChoiceCard extends AbstractTimeEaterCard {

    private Runnable onUseOrChosen;
    private String passedName;
    private String passedDesc;

    public EasyModalChoiceCard(String name, String description, Runnable onUseOrChosen, CardType type) {
        super(makeID(name), -2, type, AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.NONE, AbstractCard.CardColor.COLORLESS);
        this.name = this.originalName = passedName = name;
        this.rawDescription = passedDesc = description;
        this.onUseOrChosen = onUseOrChosen;
        initializeTitle();
        initializeDescription();
    }

    public EasyModalChoiceCard(String name, String description, Runnable onUseOrChosen) {
        this(name, description, onUseOrChosen, CardType.SKILL);
    }

    @Override
    public void onChoseThisOption() {
        onUseOrChosen.run();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        onUseOrChosen.run();
    }

    @Override
    public void upp() {

    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {
    }

    @Override
    public AbstractCard makeCopy() {
        return new EasyModalChoiceCard(passedName, passedDesc, onUseOrChosen);
    }
}
